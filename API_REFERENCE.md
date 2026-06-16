# API Reference — OneFlat

All requests go through the **API Gateway** at `http://localhost:8084`.

**Authentication:** Protected endpoints require the header:
```
Authorization: Bearer <token>
```
Obtain a token via `POST /api/v1/auth/login` or `POST /api/v1/auth/register`.

---

## Table of Contents

- [Authentication](#authentication-apiv1auth)
- [Users](#users-apiv1users)
- [Accommodations](#accommodations-apiv1accommodations)
- [Reservations](#reservations-apiv1reservations)

---

## Authentication (`/api/v1/auth`)

> All `/api/v1/auth/**` routes are public — no token required.

---

### Register a new user

```
POST /api/v1/auth/register
```

Creates a new user account and returns a JWT token, valid immediately.

**Request body:**

```json
{
  "firstName": "Alice",
  "lastName": "Dupont",
  "email": "alice@example.com",
  "phoneNumber": "+33600000000",
  "password": "secret"
}
```

| Field | Type | Required | Notes |
|---|---|---|---|
| `firstName` | String | ✓ | |
| `lastName` | String | ✓ | |
| `email` | String | ✓ | Must be unique |
| `phoneNumber` | String | ✓ | Must be unique |
| `password` | String | ✓ | Stored hashed (BCrypt) |

**Response `201 Created`:**

```json
{
  "token": "eyJhbGci...",
  "user": {
    "id": "123e4567-e89b-12d3-a456-426614174001",
    "email": "alice@example.com",
    "phoneNumber": "+33600000000",
    "firstName": "Alice",
    "lastName": "Dupont",
    "role": "CUSTOMER"
  }
}
```

**Error responses:**

| Status | Meaning |
|---|---|
| `400 Bad Request` | Missing or malformed fields |
| `409 Conflict` | Email or phone number already in use |

---

### Log in

```
POST /api/v1/auth/login
```

Authenticates an existing user and returns a JWT token.

**Request body:**

```json
{
  "email": "alice@example.com",
  "password": "secret"
}
```

**Response `200 OK`:**

```json
{
  "token": "eyJhbGci..."
}
```

**Error responses:**

| Status | Meaning |
|---|---|
| `400 Bad Request` | Malformed request |
| `401 Unauthorized` | Invalid credentials |

---

### Log out

```
POST /api/v1/auth/logout
```

Invalidates the current session server-side.

**Response `200 OK`:** `"Successfully logged out."`

---

### Validate a token

```
GET /api/v1/auth/validate
```

Used internally by the API Gateway to verify a JWT token.

**Request header:** `Authorization: Bearer <token>`

**Response `200 OK`:** `true`

**Response `401 Unauthorized`:** invalid or expired token.

---

## Users (`/api/v1/users`)

> All user endpoints require a valid JWT token.

---

### Get a user by ID

```
GET /api/v1/users/{userId}
```

**Path parameter:** `userId` (UUID)

**Response `200 OK`:**

```json
{
  "id": "123e4567-e89b-12d3-a456-426614174001",
  "email": "alice@example.com",
  "phoneNumber": "+33600000000",
  "firstName": "Alice",
  "lastName": "Dupont",
  "role": "CUSTOMER"
}
```

**Error responses:**

| Status | Meaning |
|---|---|
| `404 Not Found` | No user with this ID |

---

### Search for a user

```
GET /api/v1/users/search
```

Returns the first user matching the provided criteria. At least one parameter is required.

**Query parameters:**

| Parameter | Type | Description |
|---|---|---|
| `i` | UUID | User ID |
| `e` | String | Email address |
| `p` | String | Phone number |

**Response `200 OK`:** Same shape as [Get a user by ID](#get-a-user-by-id).

**Error responses:**

| Status | Meaning |
|---|---|
| `400 Bad Request` | No search criteria provided, or malformed input |
| `404 Not Found` | No user matches the criteria |

---

## Accommodations (`/api/v1/accommodations`)

### Public endpoints (no token required)

---

### Get an accommodation by ID

```
GET /api/v1/accommodations/{id}
```

**Path parameter:** `id` (UUID)

**Response `200 OK`:**

```json
{
  "id": "123e4567-e89b-12d3-a456-426614174000",
  "ownerId": "123e4567-e89b-12d3-a456-426614174001",
  "type": "APARTMENT",
  "location": {
    "street": "123 Main St",
    "city": "Paris",
    "postalCode": "75001",
    "country": "France"
  },
  "price": 1200.50,
  "numberOfRooms": 3,
  "numberOfBathrooms": 2,
  "area": 75,
  "description": "A spacious apartment in the heart of Paris.",
  "isVisible": true
}
```

**Error responses:**

| Status | Meaning |
|---|---|
| `404 Not Found` | No accommodation with this ID |

---

### Search accommodations

```
GET /api/v1/accommodations/search
```

Returns all visible accommodations matching the provided filters. All parameters are optional.

**Query parameters:**

| Parameter | Type | Description |
|---|---|---|
| `t` | String | Accommodation type (e.g. `APARTMENT`, `HOUSE`) |
| `c` | String | City |
| `min` | Double | Minimum price per night |
| `max` | Double | Maximum price per night |

**Response `200 OK`:** Array of accommodation objects (same shape as above).

**Error responses:**

| Status | Meaning |
|---|---|
| `400 Bad Request` | Invalid filter values |

---

### Protected endpoints (token required)

---

### Check accommodation visibility

```
GET /api/v1/accommodations/{id}/is-visible
```

Returns whether the accommodation is publicly listed. Only the owner needs this — customers
see only visible accommodations through the search endpoint.

**Request header:** `Authorization: Bearer <token>`

**Response `200 OK`:**

```json
{ "isVisible": true }
```

**Error responses:**

| Status | Meaning |
|---|---|
| `404 Not Found` | No accommodation with this ID |

---

### Create an accommodation

```
POST /api/v1/accommodations/user/{ownerId}/accommodation
```

Creates a new accommodation listing for the specified owner.

**Path parameter:** `ownerId` (UUID) — must match an existing user.

**Request body:**

```json
{
  "type": "APARTMENT",
  "location": {
    "street": "123 Main St",
    "city": "Paris",
    "postalCode": "75001",
    "country": "France"
  },
  "price": 1200.50,
  "numberOfRooms": 3,
  "numberOfBathrooms": 2,
  "area": 75,
  "description": "A spacious apartment in the heart of Paris.",
  "isVisible": false
}
```

**Response `201 Created`:** Full accommodation object (same shape as [Get an accommodation by ID](#get-an-accommodation-by-id)).

**Error responses:**

| Status | Meaning |
|---|---|
| `400 Bad Request` | Invalid field values |
| `404 Not Found` | Owner not found |

---

### Update an accommodation

```
PUT /api/v1/accommodations/user/{ownerId}/accommodation/{id}
```

Updates all fields of an existing accommodation. The request body follows the same shape as [Create an accommodation](#create-an-accommodation).

**Path parameters:** `ownerId` (UUID), `id` (UUID — accommodation ID)

**Response `200 OK`:** Updated accommodation object.

**Error responses:**

| Status | Meaning |
|---|---|
| `400 Bad Request` | Invalid field values |
| `404 Not Found` | Accommodation or owner not found |

---

### Get all accommodations by owner

```
GET /api/v1/accommodations/user/{ownerId}
```

Returns all accommodations belonging to the specified owner.

**Path parameter:** `ownerId` (UUID)

**Response `200 OK`:** Array of accommodation objects.

**Error responses:**

| Status | Meaning |
|---|---|
| `404 Not Found` | Owner not found |

---

## Reservations (`/api/v1/reservations`)

> All reservation endpoints require a valid JWT token.

### Reservation status lifecycle

```
PENDING ──► APPROVED
        ──► REJECTED
        ──► CANCELLED
```

Only `PENDING` reservations can be approved, rejected, or cancelled.
Cancelling a `REJECTED` reservation returns `403 Forbidden`.

---

### Create a reservation

```
POST /api/v1/reservations
```

**Request body:**

```json
{
  "accommodationId": "123e4567-e89b-12d3-a456-426614174000",
  "userId": "123e4567-e89b-12d3-a456-426614174001",
  "startDate": "2024-06-01T12:00:00Z",
  "endDate": "2024-06-07T12:00:00Z",
  "comment": "Looking forward to my stay"
}
```

**Response `201 Created`:**

```json
{
  "id": "123e4567-e89b-12d3-a456-426614174002",
  "accommodationId": "123e4567-e89b-12d3-a456-426614174000",
  "userId": "123e4567-e89b-12d3-a456-426614174001",
  "startDate": "2024-06-01T00:00:00Z",
  "endDate": "2024-06-07T00:00:00Z",
  "status": "PENDING",
  "comment": "Looking forward to my stay"
}
```

**Error responses:**

| Status | Meaning |
|---|---|
| `400 Bad Request` | Invalid dates or missing fields |
| `404 Not Found` | Accommodation or user not found |

---

### Get a reservation by ID

```
GET /api/v1/reservations/{reservationId}
```

**Path parameter:** `reservationId` (UUID)

**Response `200 OK`:** Reservation object (same shape as above).

**Error responses:**

| Status | Meaning |
|---|---|
| `404 Not Found` | No reservation with this ID |

---

### Get reservations for an accommodation

```
GET /api/v1/reservations/accommodation/{accommodationId}
```

**Response `200 OK`:** Array of reservation objects.

**Error responses:**

| Status | Meaning |
|---|---|
| `404 Not Found` | Accommodation not found |

---

### Get reservations for a user

```
GET /api/v1/reservations/user/{userId}
```

**Response `200 OK`:** Array of reservation objects.

**Error responses:**

| Status | Meaning |
|---|---|
| `404 Not Found` | User not found |

---

### Update a reservation

```
PUT /api/v1/reservations/{reservationId}
```

Updates dates and comment. `accommodationId` and `userId` cannot be changed after creation.

**Request body:** same shape as [Create a reservation](#create-a-reservation).

**Response `200 OK`:** Updated reservation object.

**Error responses:**

| Status | Meaning |
|---|---|
| `400 Bad Request` | Invalid dates or attempted change of immutable fields |
| `404 Not Found` | Reservation, accommodation, or user not found |

---

### Delete a reservation

```
DELETE /api/v1/reservations/{reservationId}
```

Permanently removes a reservation.

**Response `204 No Content`**

**Error responses:**

| Status | Meaning |
|---|---|
| `404 Not Found` | Reservation not found |

---

### Cancel a reservation

```
PUT /api/v1/reservations/{reservationId}/cancel
```

Transitions a `PENDING` reservation to `CANCELLED`.

**Response `200 OK`:** Updated reservation object.

**Error responses:**

| Status | Meaning |
|---|---|
| `400 Bad Request` | Reservation is not in a cancellable state |
| `403 Forbidden` | Reservation has already been rejected |
| `404 Not Found` | Reservation not found |

---

### Approve a reservation

```
PUT /api/v1/reservations/{reservationId}/approve
```

Transitions a `PENDING` reservation to `APPROVED`.

**Response `200 OK`:** Updated reservation object.

**Error responses:**

| Status | Meaning |
|---|---|
| `400 Bad Request` | Invalid state transition |
| `403 Forbidden` | Reservation is not `PENDING` |
| `404 Not Found` | Reservation not found |

---

### Reject a reservation

```
PUT /api/v1/reservations/{reservationId}/reject
```

Transitions a `PENDING` reservation to `REJECTED`.

**Response `200 OK`:** Updated reservation object.

**Error responses:**

| Status | Meaning |
|---|---|
| `400 Bad Request` | Invalid state transition |
| `403 Forbidden` | Reservation is not `PENDING` |
| `404 Not Found` | Reservation not found |

---

### Get unavailability periods for an accommodation

```
GET /api/v1/reservations/accommodation/{accommodationId}/occupied-periods
```

Returns all date ranges during which the accommodation has an active reservation.
Useful for building a booking calendar.

> This endpoint is **public** — no token required.

**Response `200 OK`:**

```json
[
  {
    "startDate": "2024-06-01T00:00:00Z",
    "endDate": "2024-06-07T00:00:00Z"
  },
  {
    "startDate": "2024-06-15T00:00:00Z",
    "endDate": "2024-06-20T00:00:00Z"
  }
]
```

**Error responses:**

| Status | Meaning |
|---|---|
| `404 Not Found` | Accommodation not found |
