import { KeyUsage } from "../model/keyUsage"

export interface Certificate{
    issuer : string,
    serialNumber: number
    country: string,
    state: string,
    organization : string,
    surname: string,
    commonName : string,
    email: string,
    startDate: string,
    endDate: string,
    keyUsage: KeyUsage[],
    certificateType : number 
}