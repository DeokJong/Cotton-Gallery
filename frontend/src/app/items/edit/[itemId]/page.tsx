import React from "react";
import EditItem from "../../_components/EditItem";

type ItemEditPageProps = {
  params: { itemId: number };
};

const page = ({ params: { itemId } }: ItemEditPageProps) => {
  return <EditItem itemId={itemId} />;
};

export default page;
