import React from "react";

type ItemEditPropsType = {
  itemId: number;
};

const EditItem = ({ itemId }: ItemEditPropsType) => {
  return <div>아이템 수정 ${itemId}</div>;
};

export default EditItem;
