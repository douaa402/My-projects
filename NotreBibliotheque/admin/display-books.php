<?php

session_start();
include "dbconnect.php";
include "header.phtml";
if (!isset($_SESSION["idB"]) || empty($_SESSION["idB"])) {
    echo "<script>alert('Veuillez vous connecter en tant qu\'administrateur.');
         window.location.href = 'loginA.php';</script>";
    exit();
  }
?>

<br><br>
<div class="container">
    <h2>Afficher Livres</h2>
    <hr>
    <form name="form1" action="" method="post">
            <table class="table">
                <tr>
                    <td>
                        <input type="text" name="t1" class="form-control" placeholder="Entrer le nom du livre"> 
                    </td>
                    <td>
                        <input type="submit" name="submit" value="Chercher"  class="btn btn-primary">
                    </td>
                </tr>
            </table>
    </form>


    <?php
    try {
        if (isset($_POST["submit"]) && !empty($_POST["t1"])) {
            // Query for searching books by name
            $query = $pdo->prepare("SELECT * FROM addbook WHERE book_name LIKE ?");
            $query->execute([$_POST["t1"] . '%']);
        } else {
            // Query for fetching all books
            $query = $pdo->query("SELECT * FROM addbook");
        }

        // Display results in a table
        echo "<table class='table table-bordered'>";
        echo "<tr>
                <th>Nom du Livre</th>
                <th>Image</th>
                <th>Auteur</th>
                <th>Publication</th>
                <th>Date d'Achat</th>
                <th>Prix</th>
                <th>Quantité Totale</th>
                <th>Quantité Disponible</th>
                <th>Supprimer</th>
                <th>Modifier</th>
              </tr>";

        while ($row = $query->fetch(PDO::FETCH_ASSOC)) {
            echo "<tr>";
            echo "<td>" . htmlspecialchars($row["book_name"]) . "</td>";
            echo "<td><img src='" . htmlspecialchars($row["book_image"]) . "' height='100' width='100'></td>";
            echo "<td>" . htmlspecialchars($row["book_author_name"]) . "</td>";
            echo "<td>" . htmlspecialchars($row["book_publication_name"]) . "</td>";
            echo "<td>" . htmlspecialchars($row["book_purchase_date"]) . "</td>";
            echo "<td>" . htmlspecialchars($row["book_price"]) . " dinars</td>";
            echo "<td>" . htmlspecialchars($row["book_quantity"]) . "</td>";
            echo "<td>" . htmlspecialchars($row["available_quantity"]) . "</td>";
            echo "<td><button type='button' class='btn btn-dark'><a href='delete_books.php?id=" . urlencode($row['id']) . "'><i class='bi bi-trash3'></i></a></button></td>";
            echo "<td><button type='button' class='btn btn-success'><a href='update_book.php?id=" . urlencode($row['id']) . "'><i class='bi bi-pencil-square'></i></a></button></td>";
            echo "</tr>";
        }

        echo "</table>";
    } catch (Exception $e) {
        echo "<div class='alert alert-danger'>Erreur: " . $e->getMessage() . "</div>";
    }
    ?>
</div>

<?php include "footer.phtml"; ?>


