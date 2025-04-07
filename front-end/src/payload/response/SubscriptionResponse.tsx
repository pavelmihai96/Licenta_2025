export default interface SubscriptionResponse {
    provider_id: number,
    username: string,
    status?: "active" | "inactive",
    index_old?: number,
    index_read?: number
}