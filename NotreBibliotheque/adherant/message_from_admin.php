<?php
// Include required files
include "dbconnect.php";
include "header.phtml";

// Start session if not already started
if (session_status() === PHP_SESSION_NONE) {
  session_start();
}
if (!isset($_SESSION["id"]) || empty($_SESSION["id"])) {
    echo "<script>alert('Veuillez vous connecter en tant qu\'un adherant.');
         window.location.href = 'loginE.php';</script>";
    exit();
  }
if (!isset($_SESSION['username'])) {
  echo "<pre>";
  print_r($_SESSION); // Display session data
  echo "</pre>";
  die("Erreur : Session non démarrée ou utilisateur non connecté.");
}

// Mark all messages as read for the current user
try {
    $query = "UPDATE messages SET read1 = 'y' WHERE username = :username";
    $stmt = $pdo->prepare($query);
    $stmt->execute([':username' => $_SESSION['username'] ?? '']); // ?? verife $_session['username']

} catch (PDOException $e) {
    die("Erreur : " . htmlspecialchars($e->getMessage()));
}
?>

<div class="container mt-4">
    <h2>Messages reçus</h2>
    <hr>
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>Nom complet</th>
                <th>Titre</th>
                <th>Message</th>
            </tr>
        </thead>
        <tbody>
            <?php
              try {
                // Fetch all messages for the current user
                $query = "SELECT * FROM messages WHERE username = :username ORDER BY id DESC";
                $stmt = $pdo->prepare($query);
                $stmt->execute([':username' => $_SESSION['username'] ?? '']);

                while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
                    // Fetch sender's details for each message
                    $querySender = "SELECT nomB, prenomB FROM `inscription-bibliothecaire` WHERE usernameB = :usernameB";
                    $stmtSender = $pdo->prepare($querySender);
                    $stmtSender->execute([':usernameB' => $row['usernameB']]);
                    $sender = $stmtSender->fetch(PDO::FETCH_ASSOC);
                    $fullname = $sender ? htmlspecialchars($sender['nomB'] . " " . $sender['prenomB']) : 'Inconnu';

                    // Display message row
                    echo "<tr>
                            <td>{$fullname}</td>
                            <td>" . htmlspecialchars($row['title']) . "</td>
                            <td>" . htmlspecialchars($row['msg']) . "</td>
                          </tr>";
                }
            } catch (PDOException $e) {
                echo "<tr><td colspan='3'>Erreur : " . htmlspecialchars($e->getMessage()) . "</td></tr>";
            }
            ?>
        </tbody>
    </table>
</div>

<?php include "footer.phtml"; ?>


