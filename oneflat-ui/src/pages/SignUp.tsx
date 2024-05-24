import React, { FormEvent, useState } from 'react'
import {
    Button,
    FormControl,
    IconButton,
    InputAdornment,
    InputLabel,
    OutlinedInput,
    TextField,
    Snackbar,
} from '@mui/material'
import Visibility from '@mui/icons-material/Visibility'
import VisibilityOff from '@mui/icons-material/VisibilityOff'
import { useNavigate } from 'react-router-dom'

interface IUser {
    email: string
    phoneNumber: string
    password: string
    firstName: string
    lastName: string
    role: RolesTypes
}

enum RolesTypes {
    CUSTOMER = 'CUSTOMER',
    ADMIN = 'ADMIN',
}

const SignUp = () => {
    const [user, setUser] = useState<IUser>({
        email: '',
        phoneNumber: '',
        password: '',
        firstName: '',
        lastName: '',
        role: RolesTypes.CUSTOMER,
    })
    const [showPassword, setShowPassword] = useState(false)
    const [openSnackbar, setOpenSnackbar] = useState(false)
    const [snackbarMessage, setSnackbarMessage] = useState('')
    const navigate = useNavigate()

    const handleInputChange = (
        e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
    ) => {
        const { name, value } = e.target
        setUser({ ...user, [name]: value })
    }

    const handleClickShowPassword = () => setShowPassword(!showPassword)

    const handleMouseDownPassword = (
        event: React.MouseEvent<HTMLButtonElement>
    ) => {
        event.preventDefault()
    }

    const handleCloseSnackbar = () => {
        setOpenSnackbar(false)
    }

    async function handleSubmitUser(
        event: FormEvent<HTMLFormElement>
    ): Promise<void> {
        event.preventDefault()
        try {
            const response = await fetch(
                'http://localhost:8082/api/v1/users/register',
                {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(user),
                }
            )

            if (!response.ok) {
                throw new Error(`HTTP Error: ${response.status}`)
            }
            const newUser = await response.json()
            console.log('New user created:', newUser)
            setSnackbarMessage('User successfully registered!')
            setOpenSnackbar(true)
            navigate('/dashboard') // Redirigez vers la page ou le chemin de votre choix
        } catch (error) {
            console.error('Error during user creation:', error)
            setSnackbarMessage('Failed to register user.')
            setOpenSnackbar(true)
        }
    }

    return (
        <div className="signup-page">
            <div className="signup-img"></div>
            <div className="signup-form">
                <div className="signup-title">
                    <h1>Welcome at</h1>
                </div>
                <form onSubmit={handleSubmitUser}>
                    <TextField
                        onChange={handleInputChange}
                        label="Email"
                        name="email"
                    />
                    <TextField
                        onChange={handleInputChange}
                        label="Phone Number"
                        name="phoneNumber"
                    />

                    <FormControl variant="outlined">
                        <InputLabel htmlFor="outlined-adornment-password">
                            Password
                        </InputLabel>
                        <OutlinedInput
                            onChange={handleInputChange}
                            id="outlined-adornment-password"
                            type={showPassword ? 'text' : 'password'}
                            name="password"
                            endAdornment={
                                <InputAdornment position="end">
                                    <IconButton
                                        aria-label="toggle password visibility"
                                        onClick={handleClickShowPassword}
                                        onMouseDown={handleMouseDownPassword}
                                        edge="end"
                                    >
                                        {showPassword ? (
                                            <VisibilityOff />
                                        ) : (
                                            <Visibility />
                                        )}
                                    </IconButton>
                                </InputAdornment>
                            }
                            label="Password"
                        />
                    </FormControl>

                    <TextField
                        onChange={handleInputChange}
                        label="First Name"
                        name="firstName"
                    />
                    <TextField
                        onChange={handleInputChange}
                        label="Last Name"
                        name="lastName"
                    />

                    <Button variant="contained" type="submit">
                        Join us!
                    </Button>
                </form>
                <Snackbar
                    open={openSnackbar}
                    autoHideDuration={6000}
                    onClose={handleCloseSnackbar}
                    message={snackbarMessage}
                />
            </div>
        </div>
    )
}

export default SignUp
