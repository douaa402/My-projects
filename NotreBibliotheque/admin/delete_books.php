<?php
session_start();
include "dbconnect.php";
if (!isset($_SESSION["idB"]) || empty($_SESSION["idB"])) {
    echo "<script>alert('Veuillez vous connecter en tant qu\'administrateur.');
         window.location.href = 'loginA.php';</script>";
    exit();
  }
if (isset($_GET["id"])) {
    // Secure the id to prevent SQL injection
    $id = intval($_GET["id"]);

    try {
        // Prepare the SQL query to delete the book
        $query = $pdo->prepare("DELETE FROM addbook WHERE id = :id");
        $query->bindParam(':id', $id, PDO::PARAM_INT);
        $query->execute();
    } catch (PDOException $e) {
        // Handle any potential database errors
        echo "Error: " . $e->getMessage();
        exit;
    }
}
?>

<script type="text/javascript">
    // Redirect to display-books.php
    window.location = "display-books.php";
</script>
