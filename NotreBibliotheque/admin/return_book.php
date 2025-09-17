<?php
session_start();
include "dbconnect.php"; // Ensure this connects to the database using PDO
include "header.phtml";
if (!isset($_SESSION["idB"]) || empty($_SESSION["idB"])) {
    echo "<script>alert('Veuillez vous connecter en tant qu\'administrateur.');
         window.location.href = 'loginA.php';</script>";
    exit();
  }
?>

<br><br>
<div class="container">
    <h2>Retourner des livres</h2>
    <hr>
    <form name="form1" action="" method="post">
        <table class="table table-bordered">
            <tr>
                <td>
                    <select name="enrollment" class="form-control">
                        <?php
                            try {
                                // Load students who borrowed books
                                $stmt = $pdo->query("SELECT DISTINCT enrollment FROM `issue_books` WHERE books_return_date IS NULL");
                                while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
                                    echo "<option>" . htmlspecialchars($row['enrollment']) . "</option>";
                                }
                            } catch (PDOException $e) {
                                // Handle the error outside the <select> dropdown
                                echo "<p>Erreur : " . htmlspecialchars($e->getMessage()) . "</p>";
                            }
                        ?>

                    </select>
                </td>
                <td>
                    <input type="submit" name="submit1" value="Chercher" class="btn btn-primary">
                </td>
            </tr>
        </table>
    </form>

    <?php
    if (isset($_POST['submit1'])) {
        // Check if enrollment is set
        if (isset($_POST['enrollment']) && !empty($_POST['enrollment'])) {
            try {
                // Secure query with prepared statement
                $stmt = $pdo->prepare("SELECT * FROM `issue_books` WHERE enrollment = :enrollment AND books_return_date IS NULL");
                $stmt->execute([':enrollment' => $_POST['enrollment']]); // Match parameter name with the query

                if ($stmt->rowCount() > 0) {
                    echo '<table class="table table-bordered">';
                    echo '<tr>';
                    echo '<th>Inscription des adhérents</th>';
                    echo '<th>Nom</th>';
                    echo '<th>Semestre</th>';
                    echo '<th>Téléphone</th>';
                    echo '<th>Email</th>';
                    echo '<th>Nom des livres</th>';
                    echo '<th>Date d\'emprunt</th>';
                    echo '<th>Action</th>';
                    echo '</tr>';

                    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
                        echo '<tr>';
                        echo '<td>' . htmlspecialchars($row['enrollment']) . '</td>';
                        echo '<td>' . htmlspecialchars($row['student_name']) . '</td>';
                        echo '<td>' . htmlspecialchars($row['student_sem']) . '</td>';
                        echo '<td>' . htmlspecialchars($row['student_contact']) . '</td>';
                        echo '<td>' . htmlspecialchars($row['student_email']) . '</td>';
                        echo '<td>' . htmlspecialchars($row['books_name']) . '</td>';
                        echo '<td>' . htmlspecialchars($row['books_issue_date']) . '</td>';
                        echo '<td><a href="return.php?id=' . htmlspecialchars($row['id']) . '" class="btn btn-success">Retourner le livre</a></td>';
                        echo '</tr>';
                    }

                    echo '</table>';
                } else {
                    echo '<p>Aucun livre en cours d\'emprunt pour cet étudiant.</p>';
                }
            } catch (PDOException $e) {
                echo '<p>Erreur : ' . htmlspecialchars($e->getMessage()) . '</p>';
            }
        } else {
            echo '<p>Veuillez sélectionner un étudiant.</p>';
        }
    }
    ?>
</div>

<?php include "footer.phtml"; ?> 
 

