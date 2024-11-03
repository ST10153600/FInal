const functions = require("firebase-functions");
const admin = require("firebase-admin");

admin.initializeApp();

// Create a Cloud Function to get exam data
exports.getExamData = functions.https.onRequest((request, response) => {
    // Allow CORS for requests from your app
    response.set('Access-Control-Allow-Origin', '*');
    response.set('Access-Control-Allow-Methods', 'GET, POST');

    // Log the incoming request
    functions.logger.log("Received a request for exam data");

    // If it's a GET request, return data
    if (request.method === "GET") {
        const examData = {
            subject: "Mathematics",
            date: "2024-11-20",
            duration: "3 hours",
        };

        // Log the response data
        functions.logger.log("Returning exam data:", examData);
        return response.status(200).json(examData);
    }

    // Log unsupported method
    functions.logger.warn(`Method not allowed: ${request.method}`);

    // Handle other request types (POST, PUT, DELETE, etc.)
    return response.status(405).send("Method Not Allowed");
});


