// This is the person api 
import express, { Request, Response} from 'express';
import {TSMap} from 'typescript-map';

const app = express();

let contacts = new TSMap([["1", "Jacob Pogulios"], ["2", "Xena"], ["3", "Marcus Bendsten"], 
    ["4", "Zorro"], ["5", "Q"], ["6", "Zorro"]
]);

// Endpoint for person api
app.get('/person', (req: Request, res: Response) => {
    res.status(200).send('Hello World');
});

// Should return all persons
app.get('/person/list', (req: Request, res: Response) => {  
    res.status(200).send(contacts.toJSON());
});

// Should return the person matching either name or key param
app.get('/person/find', (req: Request, res: Response) => {
    if (req.query.name !== undefined) {
        let foundPersons = new TSMap();
        // Iterate over map values
        for (let [key, value] of contacts.entries()) {
            if (value.toLowerCase() === req.query.name.toString().toLowerCase()) {
                foundPersons.set(key, value);
            }
        }
        if (foundPersons.length === 0) {
            res.status(200).send('null');
        } 
        else {
            res.status(200).send(foundPersons);   
        }
    }
    else if (req.query.key !== undefined) {
        if (contacts.has(req.query.key.toString())) {
            let person = contacts.get(req.query.key.toString());
            res.status(200).send(person);
        } else {
            res.status(200).send('null');
        }
    }
    else {
        res.status(404).send('404');
    }
});

app.listen(8060, () => {
    console.log('Server is running');
});