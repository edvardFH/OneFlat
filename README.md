# OneFlat

## Introduction

Ce projet est une application de location de logement basée sur l’architecture des microservices. Il comprend deux principaux services :
1. **Accommodation Service** : Gère les informations sur les logements.
2. **User Service** : Gère les informations sur les utilisateurs.

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

Base URL : `/api/v1/users`

#### 1. Créer une accommodation
- **URL** : `/api/v1/users/{ownerId}/accommodations`
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
  "description": "A spacious apartment in the heart of Paris."
}
```

- **Réponses** :
    - **201 Created** : L'accommodation a été créée avec succès.
    - **400 Bad Request** : Données invalides.
    - **404 Not Found** : Utilisateur non trouvé.

#### 2. Mettre à jour une accommodation
- **URL** : `/api/v1/users/{ownerId}/accommodations/{id}`
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
  "description": "A spacious apartment in the heart of Paris."
}
```

- **Réponses** :
    - **200 OK** : L'accommodation a été mise à jour avec succès.
    - **400 Bad Request** : Données invalides.
    - **404 Not Found** : Accommodation ou utilisateur non trouvé.

#### 3. Obtenir les accommodations par ID de propriétaire
- **URL** : `/api/v1/users/{ownerId}/accommodations`
- **Méthode HTTP** : GET
- **Description** : Récupère toutes les accommodations d'un utilisateur spécifique.
- **Réponses** :
    - **200 OK** : Liste des accommodations.
    - **404 Not Found** : Utilisateur non trouvé.

#### 4. Obtenir une accommodation par ID
- **URL** : `/api/v1/users/{ownerId}/accommodations/{id}`
- **Méthode HTTP** : GET
- **Description** : Récupère une accommodation spécifique par son ID.
- **Réponses** :
    - **200 OK** : Détails de l'accommodation.
    - **404 Not Found** : Accommodation ou utilisateur non trouvé.

#### 5. Rechercher des accommodations
- **URL** : `/api/v1/users/{ownerId}/accommodations/search`
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
