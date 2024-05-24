import React, { useEffect, useState } from 'react'
import { Container, Grid } from '@mui/material'
import AccommodationForm from '@/components/AccommodationForms/AccommodationForms'
import AccommodationList from '@/components/AccommodationList/AccommodationList'
import Layout from '@/layouts/layout'

const Dashboard: React.FC = () => {
    const [userId, setUserId] = useState<string>('')

    useEffect(() => {
        // Récupérer l'userId du localStorage lors du montage du composant
        const storedUserId = localStorage.getItem('userId')
        if (storedUserId) {
            setUserId(storedUserId)
        } else {
            console.log('No user ID found in localStorage.')
            // Gérer le cas où aucun userId n'est trouvé, par exemple rediriger vers la page de login
            // navigate('/login'); // Assurez-vous que `useNavigate` est utilisé si nécessaire
        }
    }, [])

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
