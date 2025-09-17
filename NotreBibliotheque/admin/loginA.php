
<?php
session_start();
include "loginA.phtml";
include "dbconnect.php"; // Ensure you have a valid PDO connection in dbconnect.php

if (isset($_POST["submit1"])) {
    $query = $pdo->prepare("SELECT * FROM `inscription-bibliothecaire` WHERE mailB = :mailB AND pswB = :pswB");

    $query->bindParam(':mailB', $_POST['mailB']);
    $query->bindParam(':pswB', $_POST['pswB']);

    $query->execute();

    
    if ($query->rowCount() > 0) {
        // Fetch the user data (assuming idB is the primary key for the user)
        $user = $query->fetch(PDO::FETCH_ASSOC);
        
        // Set session variable to store the user ID
        $_SESSION["idB"] = $user['idB']; // Assuming 'idB' is the primary key of the admin user
        $_SESSION["nomB"] = $user['nomB'];
        $_SESSION["prenomB"] = $user['prenomB'];
        $_SESSION["usernameB"] = $user['usernameB'];

        
        // Redirect to the admin dashboard (or any other page after login)
        echo "<script type='text/javascript'>window.location='display-student-info.php';</script>";
        exit(); // Always exit after a redirect to prevent further code execution
    } else {
        // If no matching user is found, show an error message
        echo "<div class='alert alert-danger' role='alert' style='text-align: center;'>E-mail ou mot de passe invalide</div>";
    }
}

include "layout.phtml";
?>








 