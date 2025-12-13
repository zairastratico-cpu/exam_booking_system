import Dropdown from "react-bootstrap/Dropdown";
import DropdownButton from "react-bootstrap/DropdownButton";

function BookDropdown() {
  return (
    <DropdownButton id="dropdown-basic-button" title="Seleziona fascia oraria" className=".drop" variant="light">
      <Dropdown.Item href="#/action-1">Mattina</Dropdown.Item>
      <Dropdown.Item href="#/action-2">Pomeriggio</Dropdown.Item>
      <Dropdown.Item href="#/action-3">Sera</Dropdown.Item>
    </DropdownButton>
  );
}

export default BookDropdown;
