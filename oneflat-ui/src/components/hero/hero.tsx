import Box from '@mui/material/Box'
import TextField from '@mui/material/TextField'
import FmdGoodIcon from '@mui/icons-material/FmdGood'
import { Button } from '@mui/material'

const Hero = () => {
    return (
        <div className="home-content">
            <div className="home-left">
                <div className="home-text">
                    <h1>
                        Modern living <br />
                        for everyone
                    </h1>
                    <h6>
                        We provide a complete service for the sale, purchase or
                        rental of real estate. We have been operating in Madrid
                        and Barcelona more than 15 years.
                    </h6>
                </div>
                <div className="home-input">
                    <form>
                        <Box sx={{ display: 'flex', alignItems: 'flex-end' }}>
                            <FmdGoodIcon
                                sx={{ color: 'action.active', mr: 2, my: 0.5 }}
                            />
                            <TextField
                                id="input-with-sx"
                                label="Where do you live ?"
                                variant="standard"
                                size="medium"
                            />
                            <Button variant="outlined" type="submit">
                                Search
                            </Button>
                        </Box>
                    </form>
                </div>
            </div>
            <div className="home-img">
                <img src="src/assets/pexels-expect-best-323780 2.png" alt="" />
            </div>
        </div>
    )
}

export default Hero
