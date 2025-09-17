<?php
include "dbconnect.php"; // Assurez-vous que dbconnect.php utilise PDO pour la connexion.
include "header.phtml";

// Vérification si l'utilisateur est authentifié comme administrateur
if (!isset($_SESSION["id"]) || empty($_SESSION["id"])) {
    echo "<script>alert('Veuillez vous connecter en tant qu\\'adhérant.');
         window.location.href = 'loginE.php';</script>";
    exit();
}

?>


<br><br>
<div class="container">
    <h2>Mes livres empruntés</h2>
    <hr>

    <table class="table table-bordered">
        <thead>
            <tr>
                <th>Nom des livres</th>
                <th>date d'empreinte du livre</th>
            </tr>
        </thead>
        <tbody>



                <?php
                    try 
                    {
                        // Préparer une requête pour récupérer les livres délivrés de l'utilisateur
                        $stmt = $pdo->prepare("SELECT * FROM issue_books WHERE prenom = :prenom");
                        $stmt->execute(['prenom' => $_SESSION['prenom']]);

                        // Boucle pour afficher les résultats
                        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
                            echo "<tr>";
                            echo "<td>" . htmlspecialchars($row["books_name"]) . "</td>";
                            echo "<td>" . htmlspecialchars($row["books_issue_date"]) . "</td>";
                            echo "</tr>";
                        }
                    } catch (PDOException $e) {
                        echo "<tr><td colspan='3'>Erreur : " . htmlspecialchars($e->getMessage()) . "</td></tr>";
                    }
                ?>
        </tbody>
    </table>
</div>

<?php include "footer.phtml"; ?>
