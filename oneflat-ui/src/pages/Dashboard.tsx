import React, { useEffect, useState } from 'react'
import { Container, Grid } from '@mui/material'
import AccommodationForm from '@/components/AccommodationForms/AccommodationForms'
import AccommodationList from '@/components/AccommodationList/AccommodationList'
import Layout from '@/layouts/layout'

const Dashboard: React.FC = () => {
    const userId: string = '0073589d-0074-432d-958e-f511575868c7'
    // const [userId, setUserId] = useState<string>('')

    // useEffect(() => {
    //     const storedUserId = localStorage.getItem('userId')
    //     if (storedUserId) {
    //         setUserId(storedUserId)
    //     } else {
    //         console.log('No user ID found in localStorage.')
    //     }
    // }, [])

    return (
        <Layout showHeader={true}>
            <div className="home">
                <Container>
                    <Grid
                        container
                        spacing={3}
                        paddingTop={15}
                        direction="column"
                    >
                        <Grid item xs={12}>
                            {/* Passer userId au composant de formulaire s'il est nécessaire là */}
                            <AccommodationForm ownerId={userId} />
                        </Grid>
                        <Grid item xs={12} paddingBottom={10}>
                            {/* Utiliser le même userId pour la liste d'accommodations */}
                            <AccommodationList userId={userId} />
                        </Grid>
                    </Grid>
                </Container>
            </div>
        </Layout>
    )
}

export default Dashboard
