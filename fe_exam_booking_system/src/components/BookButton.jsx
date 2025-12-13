import { Button as BsButton } from "react-bootstrap";
import { Link } from "react-router-dom";

function BookButton({ children, variant = "primary", to = null, onClick = null, className = "", ...props }) {
  // Se ha "to", è un link
  if (to) {
    return (
      <BsButton as={Link} to={to} variant={variant} className={`btn-custom ${className}`} {...props}>
        {children}
      </BsButton>
    );
  }

  // Altrimenti è un button normale
  return (
    <BsButton variant={variant} onClick={onClick} className={`btn-custom ${className}`} {...props}>
      {children}
    </BsButton>
  );
}

export default BookButton;
