import React, { useState, ChangeEvent } from 'react'
import {
    Button,
    TextField,
    FormGroup,
    FormControlLabel,
    Checkbox,
} from '@mui/material'

interface Location {
    street: string
    city: string
    postalCode: string
    country: string
}

interface Accommodation {
    type: string
    location: Location
    price: number
    numberOfRooms: number
    numberOfBathrooms: number
    area: number
    description: string
    isVisible: boolean
}

interface AccommodationFormProps {
    ownerId: string
}

const AccommodationForm: React.FC<AccommodationFormProps> = ({ ownerId }) => {
    const [accommodation, setAccommodation] = useState<Accommodation>({
        type: '',
        location: {
            street: '',
            city: '',
            postalCode: '',
            country: '',
        },
        price: 0,
        numberOfRooms: 0,
        numberOfBathrooms: 0,
        area: 0,
        description: '',
        isVisible: false,
    })

    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target
        if (name in accommodation.location) {
            setAccommodation((prevState) => ({
                ...prevState,
                location: {
                    ...prevState.location,
                    [name]: value,
                },
            }))
        } else {
            setAccommodation((prevState) => ({
                ...prevState,
                [name]: name === 'isVisible' ? e.target.checked : value,
            }))
        }
    }

    const handleCheckboxChange = (
        _event: ChangeEvent<HTMLInputElement>,
        checked: boolean
    ) => {
        setAccommodation((prevState) => ({
            ...prevState,
            isVisible: checked,
        }))
    }

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        const response = await fetch(
            `http://localhost:8080/api/v1/accommodations/user/${ownerId}/accommodation`,
            {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(accommodation),
            }
        )
        if (response.ok) {
            alert('Accommodation created successfully!')
        }
    }

    return (
        <form onSubmit={handleSubmit}>
            <FormGroup
                style={{ display: 'flex', gap: '3rem' }}
                className="inputs"
            >
                {Object.keys(accommodation.location).map((key) => (
                    <TextField
                        key={key}
                        label={key.charAt(0).toUpperCase() + key.slice(1)}
                        name={key}
                        value={accommodation.location[key as keyof Location]}
                        onChange={handleChange}
                    />
                ))}
                <TextField
                    label="Type"
                    name="type"
                    value={accommodation.type}
                    onChange={handleChange}
                />
                <TextField
                    label="Price"
                    name="price"
                    value={accommodation.price}
                    onChange={handleChange}
                    type="number"
                />
                <TextField
                    label="Number of Rooms"
                    name="numberOfRooms"
                    value={accommodation.numberOfRooms}
                    onChange={handleChange}
                    type="number"
                />
                <TextField
                    label="Number of Bathrooms"
                    name="numberOfBathrooms"
                    value={accommodation.numberOfBathrooms}
                    onChange={handleChange}
                    type="number"
                />
                <TextField
                    label="Area (sqm)"
                    name="area"
                    value={accommodation.area}
                    onChange={handleChange}
                    type="number"
                />
                <TextField
                    label="Description"
                    name="description"
                    value={accommodation.description}
                    onChange={handleChange}
                    multiline
                />
                <FormControlLabel
                    control={
                        <Checkbox
                            checked={accommodation.isVisible}
                            onChange={handleCheckboxChange}
                        />
                    }
                    label="Is Visible"
                />
                <Button type="submit" variant="contained">
                    Create Accommodation
                </Button>
            </FormGroup>
        </form>
    )
}

export default AccommodationForm
