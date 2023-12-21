import React from "react";
import PropTypes from 'prop-types';

interface FilterFormProps {
  initialValues: {
    post: { category: number }[];
    search: string;
  };
  onSubmit: (values: { post: { category: number }[]; search: string }) => void;
}

function FilterForm({ initialValues, onSubmit }: FilterFormProps) {
  const [values, setValues] = React.useState(initialValues);

  React.useEffect(() => {
    setValues(initialValues);
  }, [initialValues]);
  return (
    <form
      onSubmit={(event) => {
        event.preventDefault();
        onSubmit(values);
      }}
    >
      <fieldset>
        <div className="grid">
          <label>
            <input type="checkbox"
            checked={values.post.some((item) => item.category === 1)}
            onChange={({ target: { checked } }) =>
              checked? setValues({
              ...values,
              post: values.post.concat({ category: 1 }),})
              : setValues({
                ...values,
              post: values.post.filter((item) => item.category !== 1),})
            }
            />
            의류잡화
          </label>
          <label>
            <input type="checkbox"
            checked={values.post.some((item) => item.category === 2)}
            onChange={({ target: { checked } }) =>
              checked? setValues({
              ...values,
              post: values.post.concat({ category: 2 }),})
              : setValues({
                ...values,
              post: values.post.filter((item) => item.category !== 2),})
            }
            />
            식기
          </label>
          <label>
            <input type="checkbox"
            checked={values.post.some((item) => item.category === 3)}
            onChange={({ target: { checked } }) =>
              checked? setValues({
              ...values,
              post: values.post.concat({ category: 3 }),})
              : setValues({
                ...values,
              post: values.post.filter((item) => item.category !== 3),})
            }
            />
            전자제품
          </label>
          <label>
            <input type="checkbox"
            checked={values.post.some((item) => item.category === 4)}
            onChange={({ target: { checked } }) =>
              checked? setValues({
              ...values,
              post: values.post.concat({ category: 4 }),})
              : setValues({
                ...values,
              post: values.post.filter((item) => item.category !== 4),})
            }
            />
            헬스
          </label>
          <label>
            <input type="checkbox"
            checked={values.post.some((item) => item.category === 5)}
            onChange={({ target: { checked } }) =>
              checked? setValues({
              ...values,
              post: values.post.concat({ category: 5 }),})
              : setValues({
                ...values,
              post: values.post.filter((item) => item.category !== 5),})
            }
            />
            기타
          </label>
        </div>
      </fieldset>
      <input
        placeholder="검색어 입력"
        value={values.search}
        onChange={({ target: { value } }) =>
          setValues({ ...values, search: value })
        }
      />
      <button>검색</button>
    </form>
  );
}

export default FilterForm;