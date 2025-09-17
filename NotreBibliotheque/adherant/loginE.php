<?php
session_start();
include "loginE.phtml";
include "dbconnect.php"; 

if (isset($_POST["submit1"])) {

    $query = $pdo->prepare("SELECT * FROM `inscription-adhérent` WHERE mail = :mail AND statut = :statut");
    $query->bindParam(':mail', $_POST['mail']);
    $query->bindValue(':statut', 'oui');

    $query->execute();

    if ($query->rowCount() > 0) {
        // Fetch user data
        $user = $query->fetch(PDO::FETCH_ASSOC);
        $_SESSION['username'] = $user['username'];
        $_SESSION["id"] = $user['id']; 
        $_SESSION["nom"] = $user['nom'];
        $_SESSION["prenom"] = $user['prenom'];

        // Verify the provided password against the hashed password
        if (password_verify($_POST['psw'], $user['psw'])) {
            // If the password is correct, redirect to the next page
            echo "<script type='text/javascript'>window.location='my_issued_books.php';</script>";
            exit();
        } else {
            // If the password does not match, show an error message
            echo "<div class='alert alert-danger' role='alert' style='text-align: center;'>Mot de passe invalide</div>";
        }
    } else {
        // If no user with the given email and status is found, show an error message
        echo "<div class='alert alert-danger' role='alert' style='text-align: center;'>Utilisateur non trouvé ou statut invalide</div>";
    }
}

include "layout.phtml";
?>
