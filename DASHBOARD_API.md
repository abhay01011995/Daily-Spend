# Dashboard API Documentation

## Overview

The Dashboard API provides comprehensive financial data for user accounts, including savings, investments, budget tracking, and expense summaries.

## Base URL

```
http://localhost:8080/api/v1
```

## Endpoints

### 1. Get Dashboard Data (Current Month)

**GET** `/dashboard/account/{accountId}`

Retrieves dashboard data for the current month for a specific account.

#### Parameters

- `accountId` (path parameter): The ID of the account

#### Response

```json
{
  "totalSavings": 40110.00,
  "totalInvestment": 5000.00,
  "budgetTracker": {
    "spent": 9890.00,
    "budget": 100000.00,
    "remaining": 90110.00,
    "investment": 5000.00
  },
  "availableMonths": ["August 2025"],
  "currentMonth": "August 2025",
  "expenses": [
    {
      "category": "Investment",
      "amount": 5000.00,
      "icon": "💰"
    },
    {
      "category": "Shopping",
      "amount": 2600.00,
      "icon": "🛒"
    },
    {
      "category": "Other",
      "amount": 1480.00,
      "icon": "💸"
    },
    {
      "category": "Food",
      "amount": 810.00,
      "icon": "🍽️"
    }
  ]
}
```

### 2. Get Dashboard Data for Specific Month (Query Parameter)

**GET** `/dashboard/account/{accountId}?month={month}`

Retrieves dashboard data for a specific month for a specific account.

#### Parameters

- `accountId` (path parameter): The ID of the account
- `month` (query parameter): The month in format "MMMM yyyy" (e.g., "August 2025")

#### Example

```bash
curl -X GET "http://localhost:8080/api/v1/dashboard/account/1?month=August%202025"
```

### 3. Get Dashboard Data for Specific Month (Path Parameter)

**GET** `/dashboard/account/{accountId}/month/{month}`

Retrieves dashboard data for a specific month for a specific account.

#### Parameters

- `accountId` (path parameter): The ID of the account
- `month` (path parameter): The month in format "MMMM yyyy" (e.g., "August 2025")

#### Example

```bash
curl -X GET "http://localhost:8080/api/v1/dashboard/account/1/month/August%202025"
```

## Response Fields

### DashboardResponse

| Field | Type | Description |
|-------|------|-------------|
| `totalSavings` | BigDecimal | Total savings (income - expenses) |
| `totalInvestment` | BigDecimal | Total amount invested |
| `budgetTracker` | BudgetResponse | Budget tracking information |
| `availableMonths` | List<String> | Available months with transaction data |
| `currentMonth` | String | Current month being displayed |
| `expenses` | List<ExpenseSummaryResponse> | Expense breakdown by category |

### BudgetResponse

| Field | Type | Description |
|-------|------|-------------|
| `spent` | BigDecimal | Total amount spent in the period |
| `budget` | BigDecimal | Budget limit (currently set to 100,000) |
| `remaining` | BigDecimal | Remaining budget amount |
| `investment` | BigDecimal | Amount invested in the period |

### ExpenseSummaryResponse

| Field | Type | Description |
|-------|------|-------------|
| `category` | String | Expense category name |
| `amount` | BigDecimal | Total amount for this category |
| `icon` | String | Emoji icon representing the category |

## Error Responses

### Account Not Found (404)

```json
{
  "error": "Account not found with ID: {accountId}"
}
```

### Invalid Month Format (400)

```json
{
  "error": "Invalid month format: {month}. Expected format: 'MMMM yyyy' (e.g., 'August 2025')"
}
```

## Category Icons

The API automatically assigns emoji icons to expense categories:

- Shopping: 🛒
- Food: 🍽️
- Transportation: 🚗
- Entertainment: 🎬
- Bills: 📄
- Healthcare: 🏥
- Education: 📚
- Investment: 💰
- Hand Loan/Loan: 💳
- Other: 💸

## Testing

To test the API endpoints, you can use the following sample data:

1. Create an account:
```bash
curl -X POST "http://localhost:8080/api/v1/accounts" \
  -H "Content-Type: application/json" \
  -d '{"name": "Test User", "mobileNumber": "1234567890", "emailAddress": "test@example.com"}'
```

2. Add sample transactions:
```bash
# Income
curl -X POST "http://localhost:8080/api/v1/transactions" \
  -H "Content-Type: application/json" \
  -d '{"accountId": 1, "amount": 50000, "description": "Salary", "transactionType": "INCOME", "category": "SALARY"}'

# Expenses
curl -X POST "http://localhost:8080/api/v1/transactions" \
  -H "Content-Type: application/json" \
  -d '{"accountId": 1, "amount": 2600, "description": "Grocery Shopping", "transactionType": "EXPENSE", "category": "SHOPPING"}'
```

3. Test the dashboard endpoint:
```bash
curl -X GET "http://localhost:8080/api/v1/dashboard/account/1"
```