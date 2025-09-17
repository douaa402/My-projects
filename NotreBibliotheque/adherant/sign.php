<?php
include "sign.phtml";
include "dbconnect.php";

if (isset($_POST["submit1"])) 
{
        // Hash the password before storing it in the database
        $hashedPassword = password_hash($_POST['psw'], PASSWORD_DEFAULT); // Hash the password

        // Prepare the SQL query with placeholders for user inputs
        $query = $pdo->prepare("INSERT INTO `inscription-adhérent` (nom, prenom, username, mail, telephone,enrollment,sem, psw, statut) 
                                VALUES (:nom, :prenom, :username, :mail, :telephone,:enrollment,:sem, :psw, :statut)");

        // Bind user inputs to the placeholders, use the hashed password instead of plain text password
        $query->bindParam(':nom', $_POST['nom']);
        $query->bindParam(':prenom', $_POST['prenom']);
        $query->bindParam(':username', $_POST['username']);
        $query->bindParam(':mail', $_POST['mail']);
        $query->bindParam(':telephone', $_POST['telephone']);
        $query->bindParam(':enrollment', $_POST['enrollment']);
        $query->bindParam(':sem', $_POST['sem']);
        $query->bindParam(':psw', $hashedPassword); // Store the hashed password
        $query->bindValue(':statut','non'); // Default statut is 'non'


      // Execute the query
      try {
              if ($query->execute()) {
                  echo '<div class="alert alert-info" role="alert" style="text-align: center;">
                          Inscription réussie, vous recevrez un e-mail lorsque votre compte sera approuvé.
                        </div>';
              } else {
                  echo '<div class="alert alert-danger" role="alert" style="text-align: center;">
                          Une erreur est survenue lors de l\'inscription. Veuillez réessayer.
                        </div>';
              }
          } catch (PDOException $e) {
                echo '<div class="alert alert-danger" role="alert" style="text-align: center;">
                        Une erreur est survenue lors de l\'insertion: ' . $e->getMessage() . '
                      </div>';
            }
}
include "layout.phtml";
?>

