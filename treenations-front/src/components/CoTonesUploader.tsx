import React, {useState} from 'react';
import {
    Box,
    Button,
    Flex,
    FormControl,
    FormLabel,
    Input,
    Table,
    Tbody,
    Td,
    Th,
    Thead,
    Tr,
    VStack,
} from '@chakra-ui/react';
import {AttachmentIcon} from '@chakra-ui/icons';

interface CoTonesData {
    year: string;
    Month: string;
    minimumExpected: number;
    "COt-compensation": number;
};

const CoTonesUploader: React.FC = () => {
    const [file, setFile] = useState<File | null>(null);
    const [minimumCOTonesCompensation, setMinimumCOTonesCompensation] = useState<string>('');
    const [lowImpactMonths, setLowImpactMonths] = useState<CoTonesData[]>([]);

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        if (!file) {
            alert('Please select a file');
            return;
        }

        const formData = new FormData();
        formData.append('file', file);
        formData.append('minimumCOTonesCompensation', minimumCOTonesCompensation);

        try {
            const response = await fetch('/lowImpactMonths', {
                method: 'POST',
                body: formData,
            });

            if (!response.ok) {
                throw new Error('Failed to upload file');
            }
            debugger
            const data = await response.json();
            setLowImpactMonths(data);
        } catch (error) {
            console.error('Error:', error);
            alert('Error uploading file');
        }
    };

    return (
        <Box>
            <form onSubmit={handleSubmit}>
                <VStack spacing={4}>
                    <FormControl id="file">
                        <FormLabel>File</FormLabel>
                        <Flex alignItems="center">
                            <Input
                                type="file"
                                onChange={(e) => setFile(e.target.files && e.target.files[0] ? e.target.files[0] : null)}
                                accept=".csv,.json"
                                hidden
                                id="file-upload"
                            />
                            <label htmlFor="file-upload">
                                <Button as="span" leftIcon={<AttachmentIcon/>} size="sm">
                                    Choose File
                                </Button>
                            </label>
                            <Box ml={4}>{file ? file.name : 'No file selected'}</Box>
                        </Flex>
                    </FormControl>
                    <FormControl id="minimumCOTonesCompensation">
                        <FormLabel>Minimum CO Tones Compensation</FormLabel>
                        <Input
                            type="number"
                            value={minimumCOTonesCompensation}
                            onChange={(e) => setMinimumCOTonesCompensation(e.target.value)}
                            required
                        />
                    </FormControl>
                    <Button type="submit">Submit</Button>
                </VStack>
            </form>
            {lowImpactMonths.length > 0 && (
                <Table variant="simple" mt={8}>
                    <Thead>
                        <Tr>
                            <Th>Year</Th>
                            <Th>Month</Th>
                            <Th>Minimum expected</Th>
                            <Th>CO tones compensation reached</Th>
                        </Tr>
                    </Thead>
                    <Tbody>
                        {lowImpactMonths.map((row, index) => (
                            <Tr key={index}>
                                <Td>{row.year}</Td>
                                <Td>{row.Month}</Td>
                                <Td>{row.minimumExpected}</Td>
                                <Td>{row["COt-compensation"]}</Td>
                            </Tr>
                        ))}
                    </Tbody>
                </Table>
            )}
        </Box>
    );
};

export default CoTonesUploader;

