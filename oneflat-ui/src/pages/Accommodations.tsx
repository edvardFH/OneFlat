import { useState, useEffect } from 'react'
import { List, ListItem, ListItemText } from '@mui/material'
import Layout from '@/layouts/layout'

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

const Accommodations = () => {
    const [accommodations, setAccommodations] = useState<Accommodation[]>([]) // Added type annotation for better type checking

    useEffect(() => {
        const fetchAccommodations = async () => {
            const response = await fetch('http://localhost:3000/accommodations')
            const data: Accommodation[] = await response.json() // Ensure correct type is used for data
            setAccommodations(data)
        }

        fetchAccommodations()
    }, [])

    return (
        <Layout showHeader={true}>
            <div className="accommodations">
                <List>
                    {accommodations.map((accommodation) => (
                        <ListItem key={accommodation.id}>
                            <ListItemText
                                primary={`${accommodation.description} - ${accommodation.location.city}`}
                                secondary={`Type: ${accommodation.type} - Price: ${accommodation.price}€ - Rooms: ${accommodation.numberOfRooms} - Bathrooms: ${accommodation.numberOfBathrooms}`}
                            />
                        </ListItem>
                    ))}
                </List>
            </div>
        </Layout>
    )
}

export default Accommodations
