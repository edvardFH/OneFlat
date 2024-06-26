# OneFlat

## Introduction

Ce projet est une application de location de logement basée sur l’architecture des microservices. Il comprend trois principaux services :
1. **Accommodation Service** : Gère les informations sur les logements.
2. **User Service** : Gère les informations sur les utilisateurs.
3. **Reservation Management Serice** : Gère les réservations et la disponibilité des logements.

## Endpoints

### User Service

Base URL : `/api/v1/users`

#### 1. Créer un utilisateur
- **URL** : `/api/v1/users/register`
- **Méthode HTTP** : POST
- **Description** : Crée un nouvel utilisateur.
- **Body** : `UserCreateDTO`

```json
{
  "email": "john.doe@example.com",
  "phoneNumber": "+1234567890",
  "password": "securepassword123",
  "firstName": "John",
  "lastName": "Doe",
  "role": "CUSTOMER"
}
```

- **Réponses** :
    - **201 Created** : L'utilisateur a été créé avec succès.
    - **400 Bad Request** : Données invalides.
    - **409 Conflict** : Email ou numéro de téléphone déjà utilisé.

#### 2. Obtenir un utilisateur par ID
- **URL** : `/api/v1/users/{userId}`
- **Méthode HTTP** : GET
- **Description** : Récupère les détails d'un utilisateur spécifique par son ID.
- **Réponses** :
    - **200 OK** : Détails de l'utilisateur.
    - **404 Not Found** : Utilisateur non trouvé.

#### 3. Rechercher un utilisateur
- **URL** : `/api/v1/users/search`
- **Méthode HTTP** : GET
- **Description** : Recherche un utilisateur selon différents critères.
- **Paramètres de requête** :
    - `i` (UUID, optionnel) : ID de l'utilisateur.
    - `e` (String, optionnel) : Email.
    - `p` (String, optionnel) : Numéro de téléphone.
- **Réponses** :
    - **200 OK** : Détails de l'utilisateur correspondant aux critères de recherche.
    - **404 Not Found** : Utilisateur non trouvé.
    - **400 Bad Request** : Critères de recherche invalides.


### Accommodation Service

Base URL : `/api/v1/accommodations`

#### 1. Créer une accommodation
- **URL** : `/api/v1/accommodations/user/{ownerId}/accommodation`
- **Méthode HTTP** : POST
- **Description** : Crée une nouvelle accommodation pour un utilisateur spécifique.
- **Body** : `AccommodationRequestDTO`

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

- **Réponses** :
    - **201 Created** : L'accommodation a été créée avec succès.
    - **400 Bad Request** : Données invalides.
    - **404 Not Found** : Utilisateur non trouvé.

#### 2. Mettre à jour une accommodation
- **URL** : `/api/v1/accommodations/user/{ownerId}/accommodation/{id}`
- **Méthode HTTP** : PUT
- **Description** : Met à jour une accommodation existante.
- **Body** : `AccommodationRequestDTO`

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
  "isVisible": true
}
```

- **Réponses** :
    - **200 OK** : L'accommodation a été mise à jour avec succès.
    - **400 Bad Request** : Données invalides.
    - **404 Not Found** : Accommodation ou utilisateur non trouvé.


#### 3. Obtenir les accommodations par ID de propriétaire
- **URL** : `/api/v1/accommodations/user/{ownerId}`
- **Méthode HTTP** : GET
- **Description** : Récupère toutes les accommodations d'un utilisateur spécifique.
- **Réponses** :
    - **200 OK** : Liste des accommodations.
    - **404 Not Found** : Utilisateur non trouvé.


#### 4. Obtenir une accommodation par ID
- **URL** : `/api/v1/accommodations/{id}`
- **Méthode HTTP** : GET
- **Description** : Récupère une accommodation spécifique par son ID.
- **Réponses** :
    - **200 OK** : Détails de l'accommodation.
    - **404 Not Found** : Accommodation ou utilisateur non trouvé.


#### 5. Rechercher des accommodations
- **URL** : `/api/v1/accommodations/search`
- **Méthode HTTP** : GET
- **Description** : Recherche des accommodations selon différents critères.
- **Paramètres de requête** :
    - `t` (String, optionnel) : Type d'accommodation.
    - `c` (String, optionnel) : Ville.
    - `min` (Double, optionnel) : Prix minimum.
    - `max` (Double, optionnel) : Prix maximum.
- **Réponses** :
    - **200 OK** : Liste des accommodations correspondant aux critères de recherche.
    - **400 Bad Request** : Critères de recherche invalides.


#### 6. Vérifier la visibilité d'un logement
- **URL** : `/api/v1/accommodations/{id}/is-visible`
- **Méthode HTTP** : GET
- **Description** : Vérifie si un logement est visible.
- **Réponses** :
  - **200 OK** : Retourne `{"isVisible": true}` ou `{"isVisible": false}`.
  - **404 Not Found** : Logement non trouvé.


### Reservation Management Service

Base URL : `/api/v1/reservations`

#### 1. Créer une réservation
- **URL** : `/api/v1/reservations`
- **Méthode HTTP** : POST
- **Description** : Crée une nouvelle réservation.
- **Body** : `ReservationRequestDTO`

```json
{
  "accommodationId": "accommodation-uuid",
  "userId": "user-uuid",
  "startDate": "2024-06-01T12:00:00Z",
  "endDate": "2024-06-07T12:00:00Z",
  "comment": "Looking forward to my stay"
}
```

- **Réponses** :
  - **201 Created** : Réservation créée avec succès.
  - **400 Bad Request** : Données de réservation invalides.
  - **404 Not Found** : Logement ou utilisateur non trouvé.

```json
{
  "id": "123e4567-e89b-12d3-a456-426614174002",
  "accommodationId": "123e4567-e89b-12d3-a456-426614174000",
  "userId": "123e4567-e89b-12d3-a456-426614174001",
  "startDate": "2024-06-01T00:00:00Z",
  "endDate": "2024-06-10T00:00:00Z",
  "status": "PENDING",
  "comment": "Vacation stay"
}
```

#### 2. Obtenir une réservation par ID
- **URL** : `/api/v1/reservations/{reservationId}`
- **Méthode HTTP** : GET
- **Description** : Récupère une réservation par son ID.
- **Réponse** : `ReservationResponseDTO`
- **Réponses** :
  - **200 OK** : Réservation récupérée avec succès.
  - **404 Not Found** : Réservation non trouvée.

```json
{
  "id": "123e4567-e89b-12d3-a456-426614174002",
  "accommodationId": "123e4567-e89b-12d3-a456-426614174000",
  "userId": "123e4567-e89b-12d3-a456-426614174001",
  "startDate": "2024-06-01T00:00:00Z",
  "endDate": "2024-06-10T00:00:00Z",
  "status": "PENDING",
  "comment": "Vacation stay"
}
```

#### 3. Obtenir les réservations d'un logement
- **URL** : `/api/v1/reservations/accommodation/{accommodationId}`
- **Méthode HTTP** : GET
- **Description** : Récupère toutes les réservations d'un logement par son ID.
- **Réponse** : Liste de `ReservationResponseDTO`
- **Réponses** :
  - **200 OK** : Liste des réservations récupérée avec succès.
  - **404 Not Found** : Logement non trouvé.

```json 
[
  {
    "id": "123e4567-e89b-12d3-a456-426614174002",
    "accommodationId": "123e4567-e89b-12d3-a456-426614174000",
    "userId": "123e4567-e89b-12d3-a456-426614174001",
    "startDate": "2024-06-01T00:00:00Z",
    "endDate": "2024-06-10T00:00:00Z",
    "status": "PENDING",
    "comment": "Vacation stay"
  }
]
```

#### 4. Obtenir les réservations d'un utilisateur
- **URL** : `/api/v1/reservations/user/{userId}`
- **Méthode HTTP** : GET
- **Description** : Récupère toutes les réservations d'un utilisateur par son ID.
- **Réponse** : Liste de `ReservationResponseDTO`
- **Réponses** :
  - **200 OK** : Liste des réservations récupérée avec succès.
  - **404 Not Found** : Utilisateur non trouvé.



#### 5. Mettre à jour une réservation
- **URL** : `/api/v1/reservations/{reservationId}`
- **Méthode HTTP** : PUT
- **Description** : Met à jour une réservation existante.
- **Body** : `ReservationRequestDTO`
```json 
{
  "accommodationId": "123e4567-e89b-12d3-a456-426614174000",
  "userId": "123e4567-e89b-12d3-a456-426614174001",
  "startDate": "2024-06-05T00:00:00Z",
  "endDate": "2024-06-15T00:00:00Z",
  "comment": "Updated vacation stay"
}
```
- **Réponses** :
  - **200 OK** : Réservation mise à jour avec succès.
  - **400 Bad Request** : Données de réservation invalides ou incohérentes.
  - **404 Not Found** : Réservation, logement ou utilisateur non trouvé.



#### 6. Supprimer une réservation
- **URL** : `/api/v1/reservations/{reservationId}`
- **Méthode HTTP** : DELETE
- **Description** : Supprime une réservation par son ID.
- **Réponses** :
  - **204 No Content** : Réservation supprimée avec succès.
  - **404 Not Found** : Réservation non trouvée.


#### 7. Annuler une réservation
- **URL** : `/api/v1/reservations/{reservationId}/cancel`
- **Méthode HTTP** : PUT
- **Description** : Annule une réservation par son ID.
- **Réponse** : `ReservationResponseDTO`
- **Réponses** :
  - **200 OK** : Réservation annulée avec succès.
  - **400 Bad Request** : Annulation invalide.
  - **403 Forbidden** : Action interdite si la réservation a déjà été rejetée.
  - **404 Not Found** : Réservation non trouvée.


#### 8. Approuver une réservation
- **URL** : `/api/v1/reservations/{reservationId}/approve`
- **Méthode HTTP** : PUT
- **Description** : Approuve une réservation par son ID.
- **Réponse** : `ReservationResponseDTO`
- **Réponses** :
  - **200 OK** : Réservation approuvée avec succès.
  - **400 Bad Request** : Approvement invalide.
  - **403 Forbidden** : Action interdite si la réservation n'est pas en attente.
  - **404 Not Found** : Réservation non trouvée.


#### 9. Rejeter une réservation
- **URL** : `/api/v1/reservations/{reservationId}/reject`
- **Méthode HTTP** : PUT
- **Description** : Rejette une réservation par son ID.
- **Réponse** : `ReservationResponseDTO`
- **Réponses** :
  - **200 OK** : Réservation rejetée avec succès.
  - **400 Bad Request** : Rejet invalide.
  - **403 Forbidden** : Action interdite si la réservation n'est pas en attente.
  - **404 Not Found** : Réservation non trouvée.


#### 10. Obtenir les périodes d'indisponibilité d'un logement
- **URL** : `/api/v1/reservations/accommodation/{accommodationId}/occupied-periods`
- **Méthode HTTP** : GET
- **Description** : Récupère les périodes d'indisponibilité d'un logement par son ID.
- **Réponse** : Liste de `UnavailabilityPeriodDTO`
- **Réponses** :
  - **200 OK** : Liste des périodes d'indisponibilité récupérée avec succès.
  - **404 Not Found** : Logement non trouvé.

```json 
[
  {
    "startDate": "2024-06-01T00:00:00Z",
    "endDate": "2024-06-10T00:00:00Z"
  },
  {
    "startDate": "2024-06-15T00:00:00Z",
    "endDate": "2024-06-20T00:00:00Z"
  }
]
```
