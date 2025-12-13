import Table from "react-bootstrap/Table";
import BookButton from "./BookButton";

// Dati di esempio necessari SOLO per popolare il layout della tabella
const sessioni = [
  {
    data: "15-12-2025",
    fasciaOraria: "Mattina",
    orarioInizio: "09:30",
    postiDisponibili: 18,
    prenotabile: true, // Questo definisce se mostrare il pulsante verde
  },
  {
    data: "22-12-2025",
    fasciaOraria: "Mattina",
    orarioInizio: "09:30",
    postiDisponibili: 20,
    prenotabile: true,
  },
  {
    data: "05-01-2026",
    fasciaOraria: "Mattina",
    orarioInizio: "09:30",
    postiDisponibili: 20,
    prenotabile: true,
  },
];

// Funzione placeholder: non fa nulla, ma previene l'errore di riferimento.
// La logica di prenotazione va implementata
const handlePrenotaClick = () => {};

function BookTable() {
  return (
    <Table striped hover responsive className="text-center">
      <thead>
        <tr>
          <th>Data Sessione</th>
          <th>Fascia Oraria</th>
          <th>Orario di inizio</th>
          <th>Posti disponibili</th>
          <th>Prenotati</th>
        </tr>
      </thead>
      <tbody>
        {sessioni.map((sessione, index) => (
          <tr key={index}>
            <td>{sessione.data}</td>
            <td>{sessione.fasciaOraria}</td>
            <td>{sessione.orarioInizio}</td>
            <td>
              <span className="fw-bold">{sessione.postiDisponibili}</span>
            </td>
            <td>
              {sessione.prenotabile ? (
                <BookButton
                  variant="success"
                  size="sm"
                  //sistituire con funzione vera
                  onClick={handlePrenotaClick}
                >
                  Prenota
                </BookButton>
              ) : (
                <span className="text-danger">Non disponibile</span>
              )}
            </td>
          </tr>
        ))}
      </tbody>
    </Table>
  );
}

export default BookTable;
