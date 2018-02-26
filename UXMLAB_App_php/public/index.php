<?php
 
use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;
 
require '../vendor/autoload.php';
require_once '../includes/DbOperation.php';
 
//Creating a new app with the config to show errors
$app = new \Slim\App([
    'settings' => [
        'displayErrorDetails' => true
    ]
]);

//registering a new user
$app->post('/register', function (Request $request, Response $response) {
    if (isTheseParametersAvailable(array('id', 'password', 'name', 'email'))) {
        $requestData = $request->getParsedBody();
        $id = $requestData['id'];
        $password = $requestData['password'];
        $name = $requestData['name'];
        $email = $requestData['email'];
        $is_student = 1;
        $db = new DbOperation();
        $responseData = array();
 
        $result = $db->registerUser($id, $password, $name, $email, $is_student);
 
        if ($result == USER_CREATED) {
            $responseData['error'] = false;
            $responseData['message'] = 'Registered successfully';
            $responseData['user'] = $db->getUserById($id);
        } elseif ($result == USER_CREATION_FAILED) {
            $responseData['error'] = true;
            $responseData['message'] = 'Some error occurred';
        } elseif ($result == USER_EXIST) {
            $responseData['error'] = true;
            $responseData['message'] = 'This email already exist, please login';
        }
 
        $response->getBody()->write(json_encode($responseData));
    }
});
 
//user login route
$app->post('/login', function (Request $request, Response $response) {
    if (isTheseParametersAvailable(array('id', 'password'))) {
        $requestData = $request->getParsedBody();
        $id = $requestData['id'];
        $password = $requestData['password'];
 
        $db = new DbOperation();
 
        $responseData = array();
 
        if ($db->userLogin($id, $password)) {
            $responseData['error'] = false;
            $responseData['user'] = $db->getUserById($id);
        } else {
            $responseData['error'] = true;
            $responseData['message'] = 'Invalid id or password';
        }
 
        $response->getBody()->write(json_encode($responseData));
    }
});
 
// //getting all users
// $app->get('/users', function (Request $request, Response $response) {
//     $db = new DbOperation();
//     $users = $db->getAllUsers();
//     $response->getBody()->write(json_encode(array("users" => $users)));
// });
 
// //updating a user
// $app->post('/update/{id}', function (Request $request, Response $response) {
//     if (isTheseParametersAvailable(array('name', 'email', 'password', 'gender'))) {
//         $id = $request->getAttribute('id');
 
//         $requestData = $request->getParsedBody();
 
//         $name = $requestData['name'];
//         $email = $requestData['email'];
//         $password = $requestData['password'];
//         $gender = $requestData['gender'];
 
 
//         $db = new DbOperation();
 
//         $responseData = array();
 
//         if ($db->updateProfile($id, $name, $email, $password, $gender)) {
//             $responseData['error'] = false;
//             $responseData['message'] = 'Updated successfully';
//             $responseData['user'] = $db->getUserByEmail($email);
//         } else {
//             $responseData['error'] = true;
//             $responseData['message'] = 'Not updated';
//         }
 
//         $response->getBody()->write(json_encode($responseData));
//     }
// });
 
//function to check parameters
function isTheseParametersAvailable($required_fields)
{
    $error = false;
    $error_fields = "";
    $request_params = $_REQUEST;
 
    foreach ($required_fields as $field) {
        if (!isset($request_params[$field]) || strlen(trim($request_params[$field])) <= 0) {
            $error = true;
            $error_fields .= $field . ', ';
        }
    }
 
    if ($error) {
        $response = array();
        $response["error"] = true;
        $response["message"] = 'Required field(s) ' . substr($error_fields, 0, -2) . ' is missing or empty';
        echo json_encode($response);
        return false;
    }
    return true;
}
 
 
$app->run();