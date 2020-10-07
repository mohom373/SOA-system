// This is the bank api 
import express, { Request, Response} from 'express';
import {TSMap} from 'typescript-map';
import kafka, {KafkaClient} from 'kafka-node';

const app = express();
const client = new kafka.KafkaClient({kafkaHost: '127.0.0.1:9092'});
 
let Producer = kafka.Producer;
let producer = new Producer(client);
let channelAccess = "access-events";
let channelError = "error-events";

function log(channel: string, message: any) {
    let payloads = [
        { topic: channel, messages: message}
    ]; 
    producer.send(payloads, function (err, data) {
        console.log(data);
    });
}


producer.on('error', function (err) {})

let banks = new TSMap([["1", "SWEDBANK"], ["2", "IKANOBANKEN"], ["3", "JPMORGAN"], 
    ["4", "NORDEA"], ["5", "CITIBANK"], ["6", "HANDELSBANKEN"], ["7", "SBAB"],
    ["8", "HSBC"], ["9", "NORDNET"]
]);

// Endpoint for bank api
app.get('/bank', (req: Request, res: Response) => {
    res.status(200).send('Hello World');
});

// Should return all banks
app.get('/bank/list', (req: Request, res: Response) => { 
    log(channelAccess, JSON.stringify(banks));
    res.status(200).send(banks.toJSON());
});

// Should return the bank matching either name or key param
app.get('/bank/find', (req: Request, res: Response) => {
    if (req.query.name !== undefined) {
        let foundValue = false;
        // Iterate over map values
        for (let [key, value] of banks.entries()) {
            if (value.toLowerCase() === req.query.name.toString().toLowerCase()) {
                log(channelAccess, key);
                res.status(200).send(key);
                foundValue = true;
            }
        }
        if (!foundValue) {
            log(channelError, "Value not found");
            res.status(200).send('null');
        }
    }
    else if (req.query.key !== undefined) {
        if (banks.has(req.query.key.toString())) {
            let bank = banks.get(req.query.key.toString());
            log(channelAccess, bank);
            res.status(200).send(bank);
        } else {
            log(channelError, "Key not found");
            res.status(200).send('null');
        }
    }
    else {
        log(channelError, 404);
        res.status(404).send('404');  
    }
});

app.listen(8070, () => {
    console.log('Server is running');
});