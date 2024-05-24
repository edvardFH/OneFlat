import React, { useEffect, useState } from 'react'
import { List, ListItem, ListItemText } from '@mui/material'

interface Location {
    street: string
    city: string
    postalCode: string
    country: string
}

interface Accommodation {
    id: string
    type: string
    location: Location
    price: number
    numberOfRooms: number
    numberOfBathrooms: number
    area: number
    description: string
    isVisible: boolean
}

interface AccommodationListProps {
    userId: string
}

const AccommodationList: React.FC<AccommodationListProps> = ({ userId }) => {
    const [accommodations, setAccommodations] = useState<Accommodation[]>([])

    useEffect(() => {
        const fetchAccommodations = async () => {
            try {
                const response = await fetch(
                    `http://localhost:8080/api/v1/accommodations/user/${userId}`
                )
                if (!response.ok) {
                    throw new Error(
                        `Failed to fetch accommodations: ${response.statusText}`
                    )
                }
                const data: Accommodation[] = await response.json()
                setAccommodations(data)
            } catch (error) {
                console.error('Error fetching accommodations:', error)
            }
        }

        fetchAccommodations()
    }, [userId])

    return (
        <List>
            {accommodations.map((accommodation) => (
                <ListItem key={accommodation.id}>
                    <ListItemText
                        primary={`${accommodation.description} - ${accommodation.location.city}`}
                        secondary={`Type: ${accommodation.type} - Price: ${accommodation.price}€ - Rooms: ${accommodation.numberOfRooms}`}
                    />
                </ListItem>
            ))}
        </List>
    )
}

export default AccommodationList
