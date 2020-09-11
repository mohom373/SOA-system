// This is the bank api 
import express, { Request, Response} from 'express';
import {TSMap} from 'typescript-map';

const app = express();

let banks = new TSMap([["1", "SWEDBANK"], ["2", "IKANOBANKEN"], ["3", "JPMORGAN"], 
    ["4", "NORDEA"], ["5", "CITIBANK"], ["6", "HANDELSBANKEN"], ["7", "SBAB"],
    ["8", "HSBC"], ["9", "NORDNET"]
]);

// Endpoint for person api
app.get('/bank', (req: Request, res: Response) => {
    res.status(200).send('Hello World');
});

// Should return all persons
app.get('/bank/list', (req: Request, res: Response) => {  
    res.status(200).send(banks.toJSON());
});

// Should return the person matching either name or key param
app.get('/bank/find', (req: Request, res: Response) => {
    if (req.query.name !== undefined) {
        let foundValue = false;
        // Iterate over map values
        for (let [key, value] of banks.entries()) {
            if (value.toLowerCase() === req.query.name.toString().toLowerCase()) {
                res.status(200).send({key, value});   
                foundValue = true;
            }
        }
        if (!foundValue) {
            res.status(200).send('null');
        }
    }
    else if (req.query.key !== undefined) {
        if (banks.has(req.query.key.toString())) {
            let bank = banks.get(req.query.key.toString());
            res.status(200).send(bank);
        } else {
            res.status(200).send('null');
        }
    }
    else {
        res.status(404).send('404');  
    }
});

app.listen(8070, () => {
    console.log('Server is running');
});