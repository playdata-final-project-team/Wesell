export default interface Pageable {
  pageNumber: number;
  pageSize: number;
  sort: [];
  offset: number;
  unpaged: boolean;
  paged: true;
}
