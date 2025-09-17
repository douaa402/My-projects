<?php
include "dbconnect.php";
include "header.phtml";
if (!isset($_SESSION["idB"]) || empty($_SESSION["idB"])) {
    echo "<script>alert('Veuillez vous connecter en tant qu\'administrateur.');
         window.location.href = 'loginA.php';</script>";
    exit();
  }
?>

    <!-- Begin Page Content -->
    <br><br>
    <div class="container">

        <h2>Listes adherants</h2>
        <hr>

        <?php
                try {
                        $query = $pdo->query("SELECT * FROM `inscription-adhérent`");

                        // Start the table
                        echo "<table class='table table-bordered'>";
                        echo "<tr>";
                        echo "<th>Nom</th>";
                        echo "<th>Prénom</th>";
                        echo "<th>Username</th>";
                        echo "<th>E-mail</th>";
                        echo "<th>Telephone</th>";
                        echo "<th>Statut</th>";
                        echo "<th>Num inscription</th>";
                        echo "<th>sem</th>";
                        echo "<th>Approuver</th>";
                        echo "<th>Ne pas approuver</th>";
                        echo "</tr>";

                        // Fetch and display the data
                        while ($row = $query->fetch(PDO::FETCH_ASSOC)) {
                            echo "<tr>";
                            echo "<td>" . htmlspecialchars($row["nom"]) . "</td>";
                            echo "<td>" . htmlspecialchars($row["prenom"]) . "</td>";
                            echo "<td>" . htmlspecialchars($row["username"]) . "</td>";
                            echo "<td>" . htmlspecialchars($row["mail"]) . "</td>";
                            echo "<td>" . htmlspecialchars($row["telephone"]) . "</td>";
                            echo "<td>" . htmlspecialchars($row["statut"]) . "</td>";
                            echo "<td>" . htmlspecialchars($row["enrollment"]) . "</td>";
                            echo "<td>" . htmlspecialchars($row["sem"]) . "</td>";
                            echo "<td><a href='approuve.php?id=" . htmlspecialchars($row["id"]) . "'>Approuver</a></td>";
                            echo "<td><a href='NotApprouve.php?id=" . htmlspecialchars($row["id"]) . "'>Ne pas Approuver</a></td>";
                            echo "</tr>";
                        }

                        echo "</table>";
                    } catch (PDOException $e) {
                        echo "Error: " . $e->getMessage();
                    }
        ?> 
    </div>

<?php
include "footer.phtml";
?>