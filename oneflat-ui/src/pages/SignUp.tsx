import { Button, TextField } from "@mui/material";
import Layout from "../layouts/layout";


const SignUp = () => {
    return (
        <Layout showFooter={false} showHeader={false}>
            <div className="signup-page">
                <div className="signup-img">
                    <img src="https://cdn.pixabay.com/photo/2016/11/18/17/46/house-1836070_1280.jpg" alt="" />
                </div>
                <div className="signup-form">
                    <h1>Welcome to One Flat</h1>
                    <form>
                        <div className="signup-name">
                        <TextField id="outlined-basic" label="FirstName" variant="outlined" />
                        <TextField id="outlined-basic" label="LastName" variant="outlined" />
                        </div>
                        <TextField id="outlined-basic" label="Email" variant="outlined" />
                        <TextField id="outlined-basic" label="confirm Email" variant="outlined" />
                        <TextField id="outlined-basic" label="Password" variant="outlined" />
                        <Button variant="contained" href="">
                            Join us !
                        </Button>
                    </form>
                </div>
            </div>
        </Layout>
    );
   };
export default SignUp;