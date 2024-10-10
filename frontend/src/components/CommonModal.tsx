import React from "react";
import Modal from "react-modal";

type Propstype = {
  children?: React.ReactNode;
  isOpen: boolean;
  onRequestClose: () => void;
  style: ReactModal.Styles;
};

const CommonModal = ({ children, isOpen, onRequestClose, style }: Propstype) => {
  return (
    <Modal isOpen={isOpen} style={style} onRequestClose={onRequestClose} ariaHideApp={false}>
      {children}
    </Modal>
  );
};

export default CommonModal;
