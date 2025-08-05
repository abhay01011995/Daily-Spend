#!/bin/bash

echo "=== DailySpend Backend API Test Script ==="
echo

# Base URL
BASE_URL="http://localhost:8080/api/v1"

echo "1. Testing Health Endpoint..."
curl -s "$BASE_URL/accounts/health" | echo "Response: $(cat)"
echo
echo

echo "2. Getting Active Membership Plans..."
curl -s -X GET "$BASE_URL/membership-plans/active" | python3 -m json.tool 2>/dev/null || echo "Response received"
echo
echo

echo "3. Creating a new account (Abhay's profile)..."
curl -s -X POST "$BASE_URL/accounts" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Abhay",
    "emailAddress": "abhayshivhare19@gmail.com",
    "mobileNumber": "8959388304",
    "dateOfBirth": "1995-01-01",
    "gender": "MALE",
    "currencyCode": "INR",
    "membershipPlanId": 2
  }' | python3 -m json.tool 2>/dev/null || echo "Account creation response received"
echo
echo

echo "4. Getting account by ID (assuming ID 1)..."
curl -s -X GET "$BASE_URL/accounts/1" | python3 -m json.tool 2>/dev/null || echo "Account details response received"
echo
echo

echo "5. Getting account by email..."
curl -s -X GET "$BASE_URL/accounts/email/abhayshivhare19@gmail.com" | python3 -m json.tool 2>/dev/null || echo "Account by email response received"
echo
echo

echo "6. Getting all accounts..."
curl -s -X GET "$BASE_URL/accounts" | python3 -m json.tool 2>/dev/null || echo "All accounts response received"
echo
echo

echo "=== API Test Complete ==="
echo "Note: Make sure the application is running with 'mvn spring-boot:run' before running this script"