export default interface InvoiceResponse {
    provider_id: number,
    username: string,
    status?: "paid" | "unpaid",
    amount: number,
    due_date?: string
}