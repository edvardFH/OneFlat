import { Button } from "@mui/material";
import Layout from "../layouts/layout";
import { FormEvent, useState } from "react";

interface IUser {
    firstName: string;
    lastName: string;
    email: string;
    password: string;
    confirmPassword: string;
}

const SignUp = () => {
    const [user, setUser] = useState<IUser>({
        firstName: "",
        lastName: "",
        email: "",
        password: "",
        confirmPassword: "",
    });

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        setUser({ ...user, [e.target.name]: e.target.value });
    };

    function handleSubmitUser(event: FormEvent<HTMLFormElement>): void {
        event.preventDefault();
        fetch('http://localhost:3000/user', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(user)
        })
            .then(res => res.json())
            .then(data => console.log(data));
    }

    return (
        <Layout showFooter={false}>
            <div className="signup-page">
                <div className="signup-img">
                </div>
                <div className="signup-form">
                    <div className="signup-title">
                        <h1>Welcome Home</h1>
                    </div>  
                    <form onSubmit={handleSubmitUser}>
                        <div className="signup-name">
                            <input
                            required
                                id="firstName"
                                name="firstName"
                                value={user.firstName}
                                placeholder="First Name"
                                onChange={handleChange}
                            />        
                            <input
                            required

                                id="lastName"
                                name="lastName"
                                placeholder="Last Name"
                                value={user.lastName}
                                onChange={handleChange}
                            />                                
                        </div>
                            <input
                            required

                                    id="email"
                                    placeholder="Email"
                                    name="email"
                                    value={user.email}
                                    onChange={handleChange}
                                />        
                            <input
                            required

                                    placeholder="Password"
                                    id="password"
                                    name="password"
                                    value={user.password}
                                    onChange={handleChange}
                                />        
                            <input
                            required

                                    placeholder="Confirm password"
                                    id="confirmPassword"
                                    name="confirmPassword"
                                    value={user.confirmPassword}
                                    onChange={handleChange}
                                />        
                            <Button variant="contained" type="submit">
                                Join us!
                            </Button>
                    </form>
                </div>
            </div>
        </Layout>
    );
};

export default SignUp;
