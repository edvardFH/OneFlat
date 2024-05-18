import Card from '@mui/material/Card'
import CardContent from '@mui/material/CardContent'
import CardMedia from '@mui/material/CardMedia'
import Typography from '@mui/material/Typography'
import { CardActionArea } from '@mui/material'

interface Props {
    height: number
    src: string
    alt?: string
    data: PropsChild
}

interface PropsChild {
    title: string
    desc: string
}

const _Card = ({ height, src, alt, data }: Props) => {
    return (
        <Card sx={{ maxWidth: 345 }}>
            <CardActionArea>
                <CardMedia
                    component="img"
                    height={height}
                    image={src}
                    alt={alt}
                />
                <CardContent>
                    <Typography gutterBottom variant="h5" component="div">
                        {data.title}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                        {data.desc}
                    </Typography>
                </CardContent>
            </CardActionArea>
        </Card>
    )
}

export default _Card
