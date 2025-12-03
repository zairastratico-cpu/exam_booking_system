# 📚 Sistema di Prenotazione Esami

Sistema web per la gestione delle prenotazioni agli esami. Gli amministratori possono creare esami con posti limitati, mentre gli utenti possono iscriversi compilando un form pubblico senza necessità di registrazione.

## 🎯 Caratteristiche Principali

- **Gestione Esami** - Gli admin creano esami con informazioni dettagliate e numero massimo di partecipanti
- **Prenotazione Pubblica** - Form di iscrizione senza login/registrazione
- **Validazione Email** - Controllo duplicati per evitare iscrizioni multiple allo stesso esame
- **Gestione Automatica Posti** - I posti disponibili decrementano automaticamente
- **Cambio Stato Automatico** - L'esame passa a "completo" quando i posti si esauriscono
- **Interfaccia Responsive** - Design Bootstrap ottimizzato per tutti i dispositivi

## 🛠️ Stack Tecnologico

### Backend
- **Java 21+**
- **Spring Boot 3.x**
  - Spring Web
  - Spring Data JPA
  - Spring Validation
- **Database**: PostgreSQL in sviluppo, AWS RDS in produzione
- **Build Tool**: Maven

### Frontend
- **React 18+**
- **React Router** - Navigazione SPA
- **Bootstrap 5** - Styling e componenti UI
- **Fetch API** - HTTP client nativo
- **JavaScript ES6+**

## ❓ Decisioni da Prendere

### Campo "Codice Ricevuto" in Prenotazione
**Serve aggiungere un campo per memorizzare un codice di conferma/ricevuta?**

**Proposta**: `codice_sicurezza VARCHAR(20) UNIQUE` ricevuto da ente somministratore (es. "EXAM-2025-00001")

## 📊 Database Schema

### Tabelle

#### User (Amministratori)
| Campo    | Tipo    | Note                  |
|----------|---------|----------------------|
| id       | bigint  | Primary Key          |
| nome     | varchar | Nome admin           |
| cognome  | varchar | Cognome admin        |
| email    | varchar | Email (unique)       |

#### Esame
| Campo             | Tipo      | Note                                    |
|-------------------|-----------|-----------------------------------------|
| id                | bigint    | Primary Key                             |
| nome              | varchar   | Nome esame                              |
| descrizione       | varchar   | Descrizione dettagliata                 |
| data              | date      | Data svolgimento                        |
| ora               | time      | Ora svolgimento                         |
| max_numb          | bigint    | Capacità massima (fisso)                |
| posti_disponibili | bigint    | Posti rimanenti (decresce)              |
| stato             | enum      | disponibile, completo                   |
| user_id           | bigint    | FK → User (admin creatore)              |

#### Prenotazioni
| Campo            | Tipo      | Note                                    |
|------------------|-----------|-----------------------------------------|
| id               | bigint    | Primary Key                             |
| nome             | varchar   | Nome prenotante                         |
| cognome          | varchar   | Cognome prenotante                      |
| email            | varchar   | Email prenotante                        |
| telefono         | varchar   | Numero di telefono                      |
| tipo_documento   | enum      | CI, Passaporto, Patente                 |
| numero_documento | varchar   | Numero del documento                    |
| codice_sicurzza  | varchar   | Codice ricevuto da ente somministratore |
| created_at       | timestamp | Data/ora prenotazione                   |
| esame_id         | bigint    | FK → Esame                              |

### Constraints
```sql
-- Prevenire iscrizioni duplicate
ALTER TABLE prenotazioni 
ADD CONSTRAINT unique_email_esame UNIQUE (email, esame_id);
```

### Trigger Automatico
```sql
-- PostgreSQL - Cambio automatico stato esame
CREATE OR REPLACE FUNCTION aggiorna_stato_esame()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.posti_disponibili = 0 AND NEW.stato = 'disponibile' THEN
        NEW.stato = 'completo';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_stato_esame
BEFORE UPDATE ON esame
FOR EACH ROW
EXECUTE FUNCTION aggiorna_stato_esame();
```

## 📁 Struttura del Progetto

```
exam_booking_system/
├── .git
├── be_prenotazioni_esami/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── exambook/
│   │   │   │           ├── controller/
│   │   │   │           │   ├── EsameController.java
│   │   │   │           │   ├── PrenotazioneController.java
│   │   │   │           │   └── UserController.java
│   │   │   │           ├── model/
│   │   │   │           │   ├── User.java
│   │   │   │           │   ├── Esame.java
│   │   │   │           │   └── Prenotazione.java
│   │   │   │           ├── repository/
│   │   │   │           │   ├── UserRepository.java
│   │   │   │           │   ├── EsameRepository.java
│   │   │   │           │   └── PrenotazioneRepository.java
│   │   │   │           ├── service/
│   │   │   │           │   ├── EsameService.java
│   │   │   │           │   └── PrenotazioneService.java
│   │   │   │           ├── dto/
│   │   │   │           │   ├── PrenotazioneRequest.java
│   │   │   │           │   └── EsameResponse.java
│   │   │   │           ├── exception/
│   │   │   │           │   ├── EsameCompletoException.java
│   │   │   │           │   └── EmailDuplicataException.java
│   │   │   │           └── ExamBookingApplication.java
│   │   │   └── resources/
│   │   │       ├── application.properties
│   │   │       └── schema.sql
│   │   └── test/
│   └── pom.xml
│
└── fe_prenotazioni_esami/
    ├── public/
    ├── src/
    │   ├── components/
    │   │   ├── admin/
    │   │   │   ├── EsameForm.jsx
    │   │   │   ├── EsamiList.jsx
    │   │   │   └── Dashboard.jsx
    │   │   ├── public/
    │   │   │   ├── EsamiDisponibili.jsx
    │   │   │   ├── PrenotazioneForm.jsx
    │   │   │   └── ConfermaPrenotazione.jsx
    │   │   └── common/
    │   │       ├── Navbar.jsx
    │   │       └── Footer.jsx
    │   ├── services/
    │   │   ├── esameService.js
    │   │   └── prenotazioneService.js
    │   ├── utils/
    │   │   └── validators.js
    │   ├── App.js
    │   └── index.js
    ├── package.json
    └── README.md
```

## 🚀 Installazione e Setup

### Prerequisiti
- Java JDK 21
- Node.js 16+ e npm
- Database relazionale (PostgreSQL / AWS RDS)
- Maven 3.6+

### Backend Setup

1. **Clone del repository**
```bash
git clone <repository-url>
cd exam-booking-system/backend
```

2. **Avvio applicazione**
```bash
npm start
```

Il frontend sarà disponibile su `http://localhost:3000`

## 📡 API Endpoints

### Esami

#### Crea nuovo esame (Admin)
```http
POST /api/esami
Content-Type: application/json
Authorization: Bearer {token}

{
  "nome": "Esame di Matematica",
  "descrizione": "Esame finale primo semestre",
  "data": "2025-06-15",
  "ora": "09:00:00",
  "maxNumb": 30,
  "userId": 1
}
```

#### Lista esami disponibili (Pubblico)
```http
GET /api/esami/disponibili

Response 200:
[
  {
    "id": 1,
    "nome": "Esame di Matematica",
    "descrizione": "Esame finale primo semestre",
    "data": "2025-06-15",
    "ora": "09:00:00",
    "postiDisponibili": 25,
    "maxNumb": 30,
    "stato": "disponibile"
  }
]
```

#### Dettaglio esame
```http
GET /api/esami/{id}

Response 200:
{
  "id": 1,
  "nome": "Esame di Matematica",
  "postiDisponibili": 25,
  "stato": "disponibile"
}
```

### Prenotazioni

#### Crea prenotazione (Pubblico)
```http
POST /api/prenotazioni
Content-Type: application/json

{
  "nome": "Mario",
  "cognome": "Rossi",
  "email": "mario.rossi@email.com",
  "telefono": "+39 333 1234567",
  "tipoDocumento": "CODICE_FISCALE",
  "numeroDocumento": "RSSMRA85M01H501Z",
  "esameId": 1
}

Response 201:
{
  "id": 15,
  "nome": "Mario",
  "cognome": "Rossi",
  "email": "mario.rossi@email.com",
  "esameId": 1,
  "createdAt": "2025-12-01T14:30:00"
}

Response 409 (Email duplicata):
{
  "error": "Email già registrata per questo esame"
}

Response 410 (Esame completo):
{
  "error": "Esame completo, posti esauriti"
}
```

#### Lista prenotazioni per esame (Admin)
```http
GET /api/esami/{id}/prenotazioni
Authorization: Bearer {token}

Response 200:
[
  {
    "id": 15,
    "nome": "Mario",
    "cognome": "Rossi",
    "email": "mario.rossi@email.com",
    "telefono": "+39 333 1234567",
    "createdAt": "2025-12-01T14:30:00"
  }
]
```

## 🔐 Logica di Business

### Race Condition Management

Per gestire richieste simultanee per l'ultimo posto disponibile, viene utilizzato un **lock pessimistico**:

```java
@Transactional
public Prenotazione creaPrenotazione(PrenotazioneRequest request) {
    // Lock sulla riga esame
    Esame esame = esameRepository.findByIdWithLock(request.getEsameId())
        .orElseThrow(() -> new ResourceNotFoundException("Esame non trovato"));
    
    // Verifica email duplicata
    if (prenotazioneRepository.existsByEmailAndEsameId(
            request.getEmail(), request.getEsameId())) {
        throw new EmailDuplicataException("Email già registrata per questo esame");
    }
    
    // Verifica posti disponibili
    if (esame.getPostiDisponibili() <= 0) {
        throw new EsameCompletoException("Esame completo");
    }
    
    // Crea prenotazione
    Prenotazione prenotazione = new Prenotazione();
    prenotazione.setEmail(request.getEmail());
    prenotazione.setEsame(esame);
    // ... altri campi
    
    // Decrementa posti (trigger aggiorna stato automaticamente)
    esame.setPostiDisponibili(esame.getPostiDisponibili() - 1);
    
    prenotazioneRepository.save(prenotazione);
    
    return prenotazione;
}
```

### Validazioni

- **Email**: formato valido + unicità per esame
- **Telefono**: formato valido
- **Documento**: tipo valido + numero presente
- **Posti disponibili**: > 0 prima di creare prenotazione
- **Stato esame**: deve essere "disponibile"

## 🎨 Interfaccia Utente

### Pagine Pubbliche

1. **Lista Esami Disponibili** (`/`)
   - Card per ogni esame con info principali
   - Badge con posti disponibili
   - Pulsante "Prenota" (disabilitato se completo)

2. **Form Prenotazione** (`/prenota/:esameId`)
   - Form con validazione client-side
   - Dropdown tipo documento
   - Conferma visuale prima dell'invio

3. **Conferma Prenotazione** (`/conferma`)
   - Messaggio di successo
   - Riepilogo dati prenotazione
   - Pulsante per tornare alla lista esami

### Area Amministrazione

1. **Dashboard** (`/admin`)
   - Statistiche generali
   - Lista esami creati

2. **Crea Esame** (`/admin/esami/nuovo`)
   - Form completo per creazione esame

3. **Gestione Esami** (`/admin/esami`)
   - Tabella con tutti gli esami
   - Azioni: visualizza prenotazioni, modifica, elimina

4. **Visualizza Prenotazioni** (`/admin/esami/:id/prenotazioni`)
   - Lista completa prenotati
   - Export CSV (opzionale)

**Sviluppato con ☕ e ⚛️**
