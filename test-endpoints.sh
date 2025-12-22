#!/bin/bash

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color
BLUE='\033[0;34m'
CYAN='\033[0;36m'

BASE_URL="http://localhost:8080"
PASSED=0
FAILED=0

# Function to print section header
print_section() {
    echo ""
    echo -e "${CYAN}=========================================${NC}"
    echo -e "${CYAN}  $1${NC}"
    echo -e "${CYAN}=========================================${NC}"
    echo ""
}

# Function to print test results
print_test() {
    local test_name=$1
    local status=$2
    local response=$3
    local expected=$4
    
    if [ "$status" -eq 0 ]; then
        echo -e "${GREEN}✓ PASSED${NC}: $test_name"
        ((PASSED++))
    else
        echo -e "${RED}✗ FAILED${NC}: $test_name"
        ((FAILED++))
        if [ ! -z "$expected" ]; then
            echo -e "  ${YELLOW}Expected:${NC} $expected"
        fi
        if [ ! -z "$response" ]; then
            echo -e "  ${YELLOW}Got:${NC} $response"
        fi
    fi
}

# Function to make HTTP request and check response
test_endpoint() {
    local method=$1
    local endpoint=$2
    local data=$3
    local expected_code=$4
    local test_name=$5
    
    if [ -z "$data" ]; then
        response=$(curl -s -w "\n%{http_code}" -X $method "$BASE_URL$endpoint")
    else
        response=$(curl -s -w "\n%{http_code}" -X $method "$BASE_URL$endpoint" \
            -H "Content-Type: application/json" \
            -d "$data")
    fi
    
    http_code=$(echo "$response" | tail -n1)
    body=$(echo "$response" | sed '$d')
    
    if [ "$http_code" -eq "$expected_code" ]; then
        print_test "$test_name" 0 "$body"
        echo "$body"
    else
        print_test "$test_name" 1 "HTTP $http_code: $body" "HTTP $expected_code"
        echo ""
    fi
}

print_section "Testing SIS Backend API Endpoints"

# ==========================================
# INSTRUCTOR ENDPOINTS
# ==========================================
echo -e "${YELLOW}=== INSTRUCTOR ENDPOINTS (5 tests) ===${NC}"
echo ""

# Test 1: Get all instructors
instructor_response=$(test_endpoint "GET" "/instructor" "" 200 "GET /instructor - Get all instructors")
INSTRUCTOR_ID=$(echo "$instructor_response" | grep -o '"id":[0-9]*' | head -1 | grep -o '[0-9]*')
echo ""

# Test 2: Add a new instructor
NEW_INSTRUCTOR=$(test_endpoint "POST" "/instructor" \
    '{"name":"Test","surname":"Instructor","email":"test.instructor@example.com","password":"password123"}' \
    201 "POST /instructor - Add new instructor")
NEW_INSTRUCTOR_ID=$(echo "$NEW_INSTRUCTOR" | grep -o '"id":[0-9]*' | grep -o '[0-9]*')
echo ""

# Test 3: Update instructor
if [ ! -z "$NEW_INSTRUCTOR_ID" ]; then
    test_endpoint "PUT" "/instructor/$NEW_INSTRUCTOR_ID" \
        '{"name":"Updated","surname":"Instructor","email":"updated.instructor@example.com","password":"newpass123"}' \
        200 "PUT /instructor/{id} - Update instructor"
    echo ""
fi

# Test 4: Update non-existent instructor (should fail)
test_endpoint "PUT" "/instructor/99999" \
    '{"name":"Updated","surname":"Instructor","email":"updated.instructor@example.com","password":"newpass123"}' \
    404 "PUT /instructor/{id} - Update non-existent instructor (should fail)"
echo ""

# Test 5: Delete non-existent instructor (should fail)
test_endpoint "DELETE" "/instructor/99999" "" 404 "DELETE /instructor/{id} - Delete non-existent instructor (should fail)"
echo ""

# ==========================================
# STUDENT ENDPOINTS
# ==========================================
echo -e "${YELLOW}=== STUDENT ENDPOINTS (12 tests) ===${NC}"
echo ""

# Test 6: Get all students
student_response=$(test_endpoint "GET" "/student" "" 200 "GET /student - Get all students")
STUDENT_ID=$(echo "$student_response" | grep -o '"studentId":[0-9]*' | head -1 | grep -o '[0-9]*')
echo ""

# Test 7: Get student by ID
if [ ! -z "$STUDENT_ID" ]; then
    test_endpoint "GET" "/student?id=$STUDENT_ID" "" 200 "GET /student?id={id} - Get student by ID"
    echo ""
fi

# Test 8: Get students by name (may return empty)
test_endpoint "GET" "/student?name=John" "" 200 "GET /student?name={name} - Get students by name"
echo ""

# Test 9: Get students by surname (may return empty)
test_endpoint "GET" "/student?surname=Doe" "" 200 "GET /student?surname={surname} - Get students by surname"
echo ""

# Test 10: Get students by name and surname (may return empty)
test_endpoint "GET" "/student?name=John&surname=Doe" "" 200 "GET /student?name={name}&surname={surname} - Get students by name and surname"
echo ""

# Test 11: Add a new student
if [ ! -z "$INSTRUCTOR_ID" ]; then
    NEW_STUDENT=$(test_endpoint "POST" "/student" \
        "{\"name\":\"TestStudent\",\"surname\":\"Student\",\"email\":\"test.student@example.com\",\"birthDate\":\"2000-01-01\",\"advisorId\":$INSTRUCTOR_ID}" \
        201 "POST /student - Add new student")
    NEW_STUDENT_ID=$(echo "$NEW_STUDENT" | grep -o '"studentId":[0-9]*' | grep -o '[0-9]*')
    echo ""
fi

# Test 12: Add student with non-existent advisor (should fail)
test_endpoint "POST" "/student" \
    '{"name":"Test","surname":"Student","email":"test.student@example.com","birthDate":"2000-01-01","advisorId":99999}' \
    404 "POST /student - Add student with non-existent advisor (should fail)"
echo ""

# Test 13: Add student without required birthDate (should fail with 400)
if [ ! -z "$INSTRUCTOR_ID" ]; then
    test_endpoint "POST" "/student" \
        "{\"name\":\"Test\",\"surname\":\"Student\",\"email\":\"test.student@example.com\",\"advisorId\":$INSTRUCTOR_ID}" \
        400 "POST /student - Add student without birthDate (should fail)"
    echo ""
fi

# Test 14: Add student with invalid email (should fail with 400)
if [ ! -z "$INSTRUCTOR_ID" ]; then
    test_endpoint "POST" "/student" \
        "{\"name\":\"Test\",\"surname\":\"Student\",\"email\":\"invalid-email\",\"birthDate\":\"2000-01-01\",\"advisorId\":$INSTRUCTOR_ID}" \
        400 "POST /student - Add student with invalid email (should fail)"
    echo ""
fi

# Test 15: Update student
if [ ! -z "$NEW_STUDENT_ID" ] && [ ! -z "$INSTRUCTOR_ID" ]; then
    test_endpoint "PUT" "/student/$NEW_STUDENT_ID" \
        "{\"name\":\"UpdatedStudent\",\"surname\":\"Student\",\"email\":\"updated.student@example.com\",\"birthDate\":\"2000-01-15\",\"advisorId\":$INSTRUCTOR_ID}" \
        200 "PUT /student/{id} - Update student"
    echo ""
fi

# Test 16: Update student with non-existent advisor (should fail)
if [ ! -z "$NEW_STUDENT_ID" ]; then
    test_endpoint "PUT" "/student/$NEW_STUDENT_ID" \
        '{"name":"Updated","surname":"Student","email":"updated.student@example.com","birthDate":"2000-01-01","advisorId":99999}' \
        404 "PUT /student/{id} - Update student with non-existent advisor (should fail)"
    echo ""
fi

# Test 17: Update non-existent student (should fail)
if [ ! -z "$INSTRUCTOR_ID" ]; then
    test_endpoint "PUT" "/student/99999" \
        "{\"name\":\"Updated\",\"surname\":\"Student\",\"email\":\"updated.student@example.com\",\"birthDate\":\"2000-01-01\",\"advisorId\":$INSTRUCTOR_ID}" \
        404 "PUT /student/{id} - Update non-existent student (should fail)"
    echo ""
fi

# ==========================================
# COURSE ENDPOINTS
# ==========================================
echo -e "${YELLOW}=== COURSE ENDPOINTS (12 tests) ===${NC}"
echo ""

# Test 18: Get all courses
course_response=$(test_endpoint "GET" "/course" "" 200 "GET /course - Get all courses")
COURSE_ID=$(echo "$course_response" | grep -o '"id":[0-9]*' | head -1 | grep -o '[0-9]*')
COURSE_CODE=$(echo "$course_response" | grep -o '"code":"[^"]*"' | head -1 | sed 's/"code":"//;s/"$//')
echo ""

# Test 19: Get course by ID
if [ ! -z "$COURSE_ID" ]; then
    test_endpoint "GET" "/course?id=$COURSE_ID" "" 200 "GET /course?id={id} - Get course by ID"
    echo ""
fi

# Test 20: Get courses by code
if [ ! -z "$COURSE_CODE" ]; then
    test_endpoint "GET" "/course?code=$COURSE_CODE" "" 200 "GET /course?code={code} - Get courses by code"
    echo ""
fi

# Test 21: Get courses by instructor ID
if [ ! -z "$INSTRUCTOR_ID" ]; then
    test_endpoint "GET" "/course?instructorId=$INSTRUCTOR_ID" "" 200 "GET /course?instructorId={id} - Get courses by instructor ID"
    echo ""
fi

# Test 22: Get courses by name (partial match)
test_endpoint "GET" "/course?name=Introduction" "" 200 "GET /course?name={name} - Get courses by name"
echo ""

# Test 23: Add a new course
if [ ! -z "$INSTRUCTOR_ID" ]; then
    NEW_COURSE=$(test_endpoint "POST" "/course" \
        "{\"name\":\"Test Course\",\"code\":\"TEST101\",\"credit\":3,\"instructorId\":$INSTRUCTOR_ID}" \
        201 "POST /course - Add new course")
    NEW_COURSE_CODE=$(echo "$NEW_COURSE" | grep -o '"code":"[^"]*"' | sed 's/"code":"//;s/"$//')
    NEW_COURSE_ID=$(echo "$NEW_COURSE" | grep -o '"id":[0-9]*' | grep -o '[0-9]*')
    echo ""
fi

# Test 24: Add course with non-existent instructor (should fail)
test_endpoint "POST" "/course" \
    '{"name":"Test Course","code":"TEST102","credit":3,"instructorId":99999}' \
    404 "POST /course - Add course with non-existent instructor (should fail)"
echo ""

# Test 25: Add course without required credit (should fail with 400)
if [ ! -z "$INSTRUCTOR_ID" ]; then
    test_endpoint "POST" "/course" \
        "{\"name\":\"Test Course\",\"code\":\"TEST103\",\"instructorId\":$INSTRUCTOR_ID}" \
        400 "POST /course - Add course without credit (should fail)"
    echo ""
fi

# Test 26: Add course without required code (should fail with 400)
if [ ! -z "$INSTRUCTOR_ID" ]; then
    test_endpoint "POST" "/course" \
        "{\"name\":\"Test Course\",\"credit\":3,\"instructorId\":$INSTRUCTOR_ID}" \
        400 "POST /course - Add course without code (should fail)"
    echo ""
fi

# Test 27: Update course
if [ ! -z "$NEW_COURSE_CODE" ] && [ ! -z "$INSTRUCTOR_ID" ]; then
    test_endpoint "PUT" "/course/$NEW_COURSE_CODE" \
        "{\"name\":\"Updated Course\",\"code\":\"TEST101\",\"credit\":4,\"instructorId\":$INSTRUCTOR_ID}" \
        200 "PUT /course/{code} - Update course"
    echo ""
fi

# Test 28: Update course with non-existent instructor (should fail)
if [ ! -z "$NEW_COURSE_CODE" ]; then
    test_endpoint "PUT" "/course/$NEW_COURSE_CODE" \
        '{"name":"Updated Course","code":"TEST101","credit":4,"instructorId":99999}' \
        404 "PUT /course/{code} - Update course with non-existent instructor (should fail)"
    echo ""
fi

# Test 29: Update non-existent course (should fail)
if [ ! -z "$INSTRUCTOR_ID" ]; then
    test_endpoint "PUT" "/course/NOTEXIST999" \
        "{\"name\":\"Updated Course\",\"code\":\"NOTEXIST999\",\"credit\":4,\"instructorId\":$INSTRUCTOR_ID}" \
        404 "PUT /course/{code} - Update non-existent course (should fail)"
    echo ""
fi

# ==========================================
# ENROLLMENT ENDPOINTS
# ==========================================
echo -e "${YELLOW}=== ENROLLMENT ENDPOINTS (9 tests) ===${NC}"
echo ""

# Test 30: Get all enrollments
enrollment_response=$(test_endpoint "GET" "/enrollment" "" 200 "GET /enrollment - Get all enrollments")
ENROLLMENT_ID=$(echo "$enrollment_response" | grep -o '"id":[0-9]*' | head -1 | grep -o '[0-9]*')
echo ""

# Test 31: Get enrollments by student ID
if [ ! -z "$STUDENT_ID" ]; then
    test_endpoint "GET" "/enrollment?studentId=$STUDENT_ID" "" 200 "GET /enrollment?studentId={id} - Get enrollments by student ID"
    echo ""
fi

# Test 32: Get enrollments by course ID
if [ ! -z "$COURSE_ID" ]; then
    test_endpoint "GET" "/enrollment?courseId=$COURSE_ID" "" 200 "GET /enrollment?courseId={id} - Get enrollments by course ID"
    echo ""
fi

# Test 33: Get enrollments by student ID and course ID
if [ ! -z "$STUDENT_ID" ] && [ ! -z "$COURSE_ID" ]; then
    test_endpoint "GET" "/enrollment?studentId=$STUDENT_ID&courseId=$COURSE_ID" "" 200 "GET /enrollment?studentId={id}&courseId={id} - Get enrollments by both student and course ID"
    echo ""
fi

# Test 34: Add a new enrollment
if [ ! -z "$NEW_STUDENT_ID" ] && [ ! -z "$NEW_COURSE_ID" ]; then
    NEW_ENROLLMENT=$(test_endpoint "POST" "/enrollment" \
        "{\"studentId\":$NEW_STUDENT_ID,\"courseId\":$NEW_COURSE_ID}" \
        201 "POST /enrollment - Add new enrollment")
    NEW_ENROLLMENT_ID=$(echo "$NEW_ENROLLMENT" | grep -o '"id":[0-9]*' | grep -o '[0-9]*')
    echo ""
fi

# Test 35: Add enrollment with non-existent student (should fail)
if [ ! -z "$COURSE_ID" ]; then
    test_endpoint "POST" "/enrollment" \
        "{\"studentId\":99999,\"courseId\":$COURSE_ID}" \
        404 "POST /enrollment - Add enrollment with non-existent student (should fail)"
    echo ""
fi

# Test 36: Add enrollment with non-existent course (should fail)
if [ ! -z "$STUDENT_ID" ]; then
    test_endpoint "POST" "/enrollment" \
        "{\"studentId\":$STUDENT_ID,\"courseId\":99999}" \
        404 "POST /enrollment - Add enrollment with non-existent course (should fail)"
    echo ""
fi

# Test 37: Update enrollment grade
if [ ! -z "$NEW_ENROLLMENT_ID" ]; then
    test_endpoint "PATCH" "/enrollment/$NEW_ENROLLMENT_ID/grade?grade=85" "" 200 "PATCH /enrollment/{id}/grade - Update enrollment grade"
    echo ""
fi

# Test 38: Delete enrollment
if [ ! -z "$NEW_ENROLLMENT_ID" ]; then
    test_endpoint "DELETE" "/enrollment/$NEW_ENROLLMENT_ID" "" 200 "DELETE /enrollment/{id} - Delete enrollment"
    echo ""
fi

# ==========================================
# ANALYTICS ENDPOINTS
# ==========================================
echo -e "${YELLOW}=== ANALYTICS ENDPOINTS (1 test) ===${NC}"
echo ""

# Test 39: Get course performance summary
test_endpoint "GET" "/analytics/performance" "" 200 "GET /analytics/performance - Get course performance summary"
echo ""

# ==========================================
# CLEANUP
# ==========================================
echo -e "${YELLOW}=== CLEANUP (3 tests) ===${NC}"
echo ""

# Delete the test course we created
if [ ! -z "$NEW_COURSE_CODE" ]; then
    test_endpoint "DELETE" "/course/$NEW_COURSE_CODE" "" 200 "DELETE /course/{code} - Cleanup: Delete test course"
    echo ""
fi

# Delete the test student we created
if [ ! -z "$NEW_STUDENT_ID" ]; then
    test_endpoint "DELETE" "/student/$NEW_STUDENT_ID" "" 200 "DELETE /student/{id} - Cleanup: Delete test student"
    echo ""
fi

# Delete the test instructor we created
if [ ! -z "$NEW_INSTRUCTOR_ID" ]; then
    test_endpoint "DELETE" "/instructor/$NEW_INSTRUCTOR_ID" "" 200 "DELETE /instructor/{id} - Cleanup: Delete test instructor"
    echo ""
fi

# ==========================================
# SUMMARY
# ==========================================
print_section "TEST SUMMARY"

echo -e "${GREEN}Passed: $PASSED${NC}"
echo -e "${RED}Failed: $FAILED${NC}"
TOTAL=$((PASSED + FAILED))
echo "Total: $TOTAL"
echo ""

if [ $FAILED -eq 0 ]; then
    echo -e "${GREEN}✓ All tests passed!${NC}"
    echo ""
    exit 0
else
    PASS_RATE=$(awk "BEGIN {printf \"%.1f\", ($PASSED/$TOTAL)*100}")
    echo -e "${RED}✗ Some tests failed (${PASS_RATE}% pass rate)${NC}"
    echo ""
    exit 1
fi
