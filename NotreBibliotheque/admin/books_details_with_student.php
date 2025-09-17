<?php
include "header.phtml";
include "dbconnect.php"

?>

<?php

            $i = 0;
            try {
                // Requête pour récupérer tous les livres
                $stmt = $pdo->query("SELECT * FROM addbook");

                echo "<table class='table table-bordered'>";
                echo "<tr>";

                while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
                    $i++;
                    echo "<td>";
                    echo "<img src='../admin/" . htmlspecialchars($row['book_image']) . "' height='100' width='100'>";
                    echo "<br>";
                    echo "<b>" . htmlspecialchars($row['book_name']) . "</b>";
                    echo "<br>";
                    echo "<b>Total Books: " . htmlspecialchars($row['book_quantity']) . "</b>";
                    echo "<br>";
                    echo "<b>Available: " . htmlspecialchars($row['available_quantity']) . "</b>";
                    echo "<br>";
                    echo "<a href='all_student_of_this_books.php?book_name=" . urlencode($row['book_name']) . "' style='color:blue'>Les etudiants ont  ce livre</a>";
                    echo "</td>";

                    // Chaque ligne contient 5 livres
                    if ($i == 5) {
                        echo "</tr><tr>";
                        $i = 0;
                    }
                }

                echo "</tr>";
                echo "</table>";
            } catch (PDOException $e) {
                echo "Erreur : " . htmlspecialchars($e->getMessage());
            }
?>

<?php
include "footer.phtml";
?>

            

 