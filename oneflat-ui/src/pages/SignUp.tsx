import React, { FormEvent, useState } from 'react'
import {
    Button,
    FormControl,
    IconButton,
    InputAdornment,
    InputLabel,
    MenuItem,
    OutlinedInput,
    Select,
    SelectChangeEvent,
    TextField,
} from '@mui/material'
import Visibility from '@mui/icons-material/Visibility'
import VisibilityOff from '@mui/icons-material/VisibilityOff'

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

    const handleSelectChange = (e: SelectChangeEvent<RolesTypes>) => {
        setUser({ ...user, role: e.target.value as RolesTypes })
    }

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

    async function handleSubmitUser(
        event: FormEvent<HTMLFormElement>
    ): Promise<void> {
        event.preventDefault()
        try {
            const response = await fetch('http://localhost:8082/api/v1/users', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(user),
            })

            if (!response.ok) {
                throw new Error(`Erreur HTTP: ${response.status}`)
            }
            console.log(user)
            console.log(response)
            const newUser = await response.json()
            console.log('Nouvel utilisateur créé:', newUser)
        } catch (error) {
            console.error("Erreur lors de la création de l'utilisateur:", error)
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
                    <div className="box-btn">
                        <FormControl>
                            <Select
                                labelId="demo-simple-select-helper-label"
                                id="demo-simple-select-helper"
                                value={user.role}
                                onChange={handleSelectChange}
                            >
                                <MenuItem value={RolesTypes.ADMIN}>
                                    {RolesTypes.ADMIN}
                                </MenuItem>
                                <MenuItem value={RolesTypes.CUSTOMER}>
                                    {RolesTypes.CUSTOMER}
                                </MenuItem>
                            </Select>
                        </FormControl>
                    </div>
                    <Button variant="contained" type="submit">
                        Join us!
                    </Button>
                </form>
            </div>
        </div>
    )
}

export default SignUp
