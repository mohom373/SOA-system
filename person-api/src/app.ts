// This is the person api 
import express, { Request, Response} from 'express';
const app = express();


app.get('/', (req: Request, res: Response) => {
    res.status(200).send('Hello World');
});

app.listen(8000, () => {
    console.log('Server is running');
});

