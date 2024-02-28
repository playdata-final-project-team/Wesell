import React from 'react';

const CommonTableRow = ({ children }) => {
  return (
    <td className="common-table-column">
      {
        children
      }
    </td>
  )
}

export default CommonTableRow;