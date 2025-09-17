<?php
include "header.phtml";
include "dbconnect.php";
if (!isset($_SESSION["idB"]) || empty($_SESSION["idB"])) {
  echo "<script>alert('Veuillez vous connecter en tant qu\'administrateur.');
       window.location.href = 'loginA.php';</script>";
  exit();
}
?>
<br> <br> <br>
<div class="container">
<?php

// Préparer et exécuter la requête
$stmt = $pdo->prepare("SELECT * FROM issue_books WHERE books_name = :books_name AND books_return_date IS NULL");
$stmt->execute(['books_name' => $_GET['book_name']]);

// Générer le tableau HTML
echo "<table class='table table-bordered'>";
echo "<tr>
        <th>Nom et Prenom</th>
        <th>Numero inscription</th>
        <th>Nom livre</th>
        <th>Email</th>
        <th>Telephone</th>
        <th>Date publication livre</th>
      </tr>";

// Parcourir les résultats
while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
    echo "<tr>
            <td>{$row['student_name']}</td>
            <td>{$row['enrollment']}</td>
            <td>{$row['books_name']}</td>
            <td>{$row['student_email']}</td>
            <td>{$row['student_contact']}</td>
            <td>{$row['books_issue_date']}</td>
          </tr>";
}

echo "</table>";
?>
</div>

<?php
include "footer.phtml";
?>

