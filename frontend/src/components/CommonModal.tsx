import React from "react";
import ReactModal from "react-modal";

type Propstype = {
  children?: React.ReactNode;
  isOpen: boolean;
  onRequestClose: () => void;
  style: ReactModal.Styles;
};

const CommonModal = ({ children, isOpen, onRequestClose, style }: Propstype) => {
  return (
    <ReactModal isOpen={isOpen} style={style} onRequestClose={onRequestClose} ariaHideApp={false}>
      {children}
    </ReactModal>
  );
};

export default CommonModal;
