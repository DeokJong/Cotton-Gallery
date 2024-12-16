import React from "react";
import ItemDetail from "../../_components/ItemDetail";

type ItemDetailPageProps = {
  params: { itemId: number };
};

const page = ({ params: { itemId } }: ItemDetailPageProps) => {
  return <ItemDetail itemId={itemId} />;
};

export default page;
