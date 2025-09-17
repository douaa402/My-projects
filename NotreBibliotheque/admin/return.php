<?php
session_start();
include "dbconnect.php"; // Ensure dbconnect.php establishes a PDO connection.
if (!isset($_SESSION["idB"]) || empty($_SESSION["idB"])) {
    echo "<script>alert('Veuillez vous connecter en tant qu\'administrateur.');
         window.location.href = 'loginA.php';</script>";
    exit();
  }
$id = intval($_GET["id"]);
if (!isset($_GET['id'])) {
    die("Error: No ID provided.");
}

$id = intval($_GET["id"]); // Validate and sanitize the ID parameter.
$a = date("Y-m-d"); // Use the `Y-m-d` format for database compatibility.

try {
    // Check if the PDO connection is correctly set.
    if (!isset($pdo)) {
        throw new Exception("Database connection not established.");
    }

    // Prepare the SQL query using placeholders to prevent SQL injection.
    $stmt = $pdo->prepare("UPDATE issue_books SET books_return_date = :return_date WHERE id = :id");
    $stmt->bindParam(':return_date', $a);
    $stmt->bindParam(':id', $id, PDO::PARAM_INT);

    if ($stmt->execute()) {
        // Redirect on successful execution.
        echo "<script>window.location='return_book.php';</script>";
    } else {
        throw new Exception("Query execution failed.");
    }
} catch (Exception $e) {
    echo "Error: " . $e->getMessage();
}
$books_name = "";

try {
    // Inclure la connexion PDO
    include "dbconnect.php";

    // Préparer et exécuter la requête pour récupérer le nom du livre
    $stmt = $pdo->prepare("SELECT books_name FROM issue_books WHERE id = :id");
    $stmt->execute([':id' => $id]);

    // Récupérer le résultat
    if ($row = $stmt->fetch()) {
        $books_name = $row['books_name'];
    }

    // Si le livre est trouvé, mettre à jour la quantité disponible
    if (!empty($books_name)) {
        $updateStmt = $pdo->prepare("
            UPDATE addbook
            SET available_quantity = available_quantity + 1 
            WHERE book_name = :books_name
        ");
        $updateStmt->execute([':books_name' => $books_name]);
    }
} catch (PDOException $e) {
    // Gestion des erreurs
    echo "Erreur : " . htmlspecialchars($e->getMessage());
}
?>































