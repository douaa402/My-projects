<?php

include "dbconnect.php";
include "header.phtml";
?>

<div class="container">
        <br> <br>
        <h2>Rechercher Livres</h2>
        <hr>

        <form action="" name="form" method="post">
            <table class="table">
                <tr>
                    <td>
                       <input type="text" placeholder="entrer nom livre" name="t1" required class="form-control"> 
                    </td>
                    <td><input type="submit" name="submit" value="rechercher" class="btn btn-primary"></td>
                </tr>
            </table>
        </form>

    <?php
        if (isset($_POST["submit"])) {
            try {

                    $i = 0;
                    $query = "SELECT * FROM addbook";
                    $params = [];

                    // Si une recherche est effectuée
                    if (!empty($_POST['t1'])) {
                        $query .= " WHERE book_name LIKE :book_name";
                        $params[':book_name'] = '%' . $_POST['t1'] . '%';      
                    }

                    $stmt = $pdo->prepare($query);
                    $stmt->execute($params);                                     //$stmt->execute([':book_name' => '%' . $_POST['t1'] . '%']);

                    // Générer le tableau HTML
                    echo "<table class='table table-bordered'>";
                    echo "<tr>";

                    while ($row = $stmt->fetch()) {
                        $i++;
                        echo "<td>";
                        echo "<img src='../admin/" . htmlspecialchars($row["book_image"]) . "' height='100' width='100'>";
                        echo "<br>";
                        echo "<b>" . htmlspecialchars($row["book_name"]) . "</b>";
                        echo "<br>";
                        echo "<b>" . "Available: " . htmlspecialchars($row["available_quantity"]) . "</b>";
                        echo "</td>";

                        // Nouvelle ligne après 5 colonnes
                        if ($i == 5) {
                            echo "</tr><tr>";
                            $i = 0;
                        }
                    }

                    // Fermer le tableau
                    echo "</tr>";
                    echo "</table>";

                } catch (PDOException $e) {
                    echo "Erreur : " . htmlspecialchars($e->getMessage());
                }
        }
    ?>
</div>

<?php
include "footer.phtml";
?>

            

 
