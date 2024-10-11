import CommonModal from "@/components/CommonModal";
import React, { SetStateAction } from "react";
import DaumPostcodeEmbed, { Address } from "react-daum-postcode";

type PropsType = {
  isModalOpen: boolean;
  setIsModalOpen: (value: SetStateAction<boolean>) => void;
  setZipcode: (zipcode: string) => void;
  setStreet: (street: string) => void;
};

const addressModalStyles: ReactModal.Styles = {
  overlay: {
    backgroundColor: " rgba(0, 0, 0, 0.4)",
    width: "100%",
    height: "100vh",
    zIndex: "10",
    position: "fixed",
    top: "0",
    left: "0"
  },
  content: {
    width: "36.25rem",
    height: "50%",
    zIndex: "11",
    position: "absolute",
    top: "50%",
    left: "50%",
    transform: "translate(-50%, -50%)",
    borderRadius: "10px",
    backgroundColor: "rgba(255, 255, 255, 100)"
  }
};

const AddressModal = ({ isModalOpen, setIsModalOpen, setZipcode, setStreet }: PropsType) => {
  const handleComplete = (data: Address) => {
    // let fullAddress = data.address;
    // let extraAddress = "";

    // if (data.addressType === "R") {
    //   if (data.bname !== "") {
    //     extraAddress += data.bname;
    //   }
    //   if (data.buildingName !== "") {
    //     extraAddress += extraAddress !== "" ? `, ${data.buildingName}` : data.buildingName;
    //   }
    //   fullAddress += extraAddress !== "" ? ` (${extraAddress})` : "";
    // }

    console.log(data.address, data.zonecode); // e.g. '서울 성동구 왕십리로2길 20 (성수동1가)'
    setIsModalOpen(false);
    setStreet(data?.address);
    setZipcode(data?.zonecode);
  };

  return (
    <div>
      <CommonModal isOpen={isModalOpen} onRequestClose={() => setIsModalOpen(false)} style={addressModalStyles}>
        <DaumPostcodeEmbed onComplete={handleComplete} autoClose={true} />
      </CommonModal>
    </div>
  );
};

export default AddressModal;
