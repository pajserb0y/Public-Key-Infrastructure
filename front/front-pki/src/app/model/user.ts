export interface User {
    id: number;
    firstName: string;
    lastName: string;
    role: string;
    jwtToken: string;
    email : string;
    password : string;
}