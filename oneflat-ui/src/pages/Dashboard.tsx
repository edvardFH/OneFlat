import AccommodationForm from '@/components/AccommodationForms/AccommodationForms'
import AccommodationList from '@/components/AccommodationList/AccommodationList'
import React from 'react'
import { Container, Grid } from '@mui/material'

const Dashboard: React.FC = () => {
    const ownerId = 'd178c372-c266-4d8d-b04c-2718c9347695'
    return (
        <Container>
            <Grid container spacing={3}>
                <Grid item xs={12} md={6}>
                    <AccommodationForm ownerId={ownerId} />
                </Grid>
                <Grid item xs={12} md={6}>
                    <AccommodationList userId={parseInt(ownerId)} />
                </Grid>
            </Grid>
        </Container>
    )
}

export default Dashboard
